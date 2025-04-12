package com.alterdekim.javabot.components;

import com.alterdekim.javabot.bot.*;
import com.alterdekim.javabot.Commands;
import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.TelegramConfig;
import com.alterdekim.javabot.bot.cards.*;
import com.alterdekim.javabot.entities.*;
import com.alterdekim.javabot.service.*;
import com.alterdekim.javabot.util.*;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
public class BunkerBot extends TelegramLongPollingBot {
    private final TelegramConfig telegramConfig;
    private String groupId = "";

    private GameState gameState;

    private double last_p = -1;

    public List<Player> players;

    private final BioService bioService;
    public final HealthService healthService;
    private final HobbyService hobbyService;
    private final LuggageService luggageService;
    private final WorkService workService;
    public final TextDataValService textDataValService;
    private final DisasterService disasterService;
    private final SynergyService synergyService;
    private List<Class<? extends ActionCard>> actionCards;

    public final RandomComponent random;

    private DayNightFields dayNightFields;

    private ConcurrentLinkedQueue<BotApiMethod<? extends Serializable>> linkedQueue;

    public LiveFormula liveFormula;

    @SuppressWarnings("deprecation")
    public BunkerBot(TelegramConfig telegramConfig,
                     BioService bioService,
                     HealthService healthService,
                     HobbyService hobbyService,
                     LuggageService luggageService,
                     WorkService workService,
                     TextDataValService textDataValService,
                     DisasterService disasterService,
                     SynergyService synergyService,
                     RandomComponent randomComponent) {
        this.telegramConfig = telegramConfig;
        this.players = new ArrayList<>();
        this.gameState = GameState.NONE;
        this.bioService = bioService;
        this.healthService = healthService;
        this.hobbyService = hobbyService;
        this.luggageService = luggageService;
        this.workService = workService;
        this.textDataValService = textDataValService;
        this.disasterService = disasterService;
        this.synergyService = synergyService;
        this.actionCards = new ArrayList<>(List.of(Sabotage.class)); // ScannerCard.class, RandomHIVCard.class, ChangeWorksCard.class,
        this.random = randomComponent;
        this.dayNightFields = new DayNightFields();
        this.linkedQueue = new ConcurrentLinkedQueue<>();
    }

    @Scheduled(fixedRate = 150)
    private void executeApi() {
        if( !this.linkedQueue.isEmpty() )
            sendApiMethodAsync(this.linkedQueue.poll());
    }


    @Override
    public String getBotUsername() {
        return telegramConfig.getBotLogin();
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getBotToken() {
        return telegramConfig.getBotToken();
    }

    private Long getMasterId() {
        return Long.parseLong(telegramConfig.getMasterId());
    }

    private void joinGame(User user, Integer msgid) {
        if( gameState != GameState.JOINING ) return;

        if( !hasPlayerWithId(user.getId()) ) {
            if( canWrite(user) ) {
                this.players.add(new Player(user.getId(), user.getFirstName()));
                sendApi(
                        new SendMessage(
                                groupId,
                                String.format(
                                        Constants.JOINED_THE_GAME,
                                        user.getFirstName(),
                                        players.size()
                                )
                        )
                );
                return;
            }

            if( msgid != 0 ) {
                SendMessage sm = new SendMessage(groupId, Constants.CANT_JOIN_NOT_STARTED);
                sm.setReplyToMessageId(msgid);
                sendApi(sm);
                return;
            }
            SendMessage sm = new SendMessage(groupId, BotUtils.mentionUser(user.getUserName()) + Constants.CANT_JOIN_NOT_STARTED);
            sendApi(sm);
            return;
        }
        sendApi(
                new SendMessage(
                        groupId,
                        Constants.ALREADY_IN_GAME
                )
        );
    }

    private Boolean canWrite(User u) {
        SendMessage message = new SendMessage(u.getId()+"", ".");
        message.disableNotification();
        Message mr;
        try {
            mr = sendApiMethod(message);
        } catch ( Exception e ) {
            return false;
        }
        sendApi(new DeleteMessage(u.getId()+"", mr.getMessageId()));
        return true;
    }

    private Boolean isAdmin(User u) {
        GetChatMember chatMember = new GetChatMember(groupId, u.getId());
        try {
            ChatMember cm = sendApiMethod(chatMember);
            return cm.getStatus().equals("creator") || cm.getStatus().startsWith("admin");
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean isNoOneHasScripts() {
        return players.stream().allMatch(p -> p.getScripts().isEmpty());
    }

    private void startGame() {
        if( gameState != GameState.JOINING )
            return;
        if(players.size() < 1) { // TODO: change to 2
            sendApi(new SendMessage(groupId, Constants.PLAYERS_LESS_THAN_ZERO));
            return;
        }
        Collections.shuffle(players);
        this.gameState = GameState.STARTED;
        this.liveFormula = new LiveFormula();
        this.liveFormula.setPlayerList(players);
        this.liveFormula.setSynergies(synergyService.getAllSynergies());
        Disaster d = (Disaster) BotUtils.getRandomFromList(disasterService.getAllDisasters(), random);
        sendApi(new SendMessage(groupId, getStringById(d.getDescTextId())));
        //sendApi(new SendMessage(groupId, String.format(Constants.BUNKER_STATS, )));
        List<Bio> bios = bioService.getAllBios();
        List<Work> works = workService.getAllWorks();
        List<Luggage> luggs = luggageService.getAllLuggages();
        List<Hobby> hobbies = hobbyService.getAllHobbies();
        List<Health> healths = healthService.getAllHealth();
        for( int i = 0; i < players.size(); i++ ) {
            players.get(i).setAge(random.nextInt(57)+18);
            players.get(i).setGender((Bio) BotUtils.getRandomFromList(bios, random));
            players.get(i).setWork((Work) BotUtils.getRandomFromList(works, random));
            players.get(i).setLuggage((Luggage) BotUtils.getRandomFromList(luggs, random));
            players.get(i).setHobby((Hobby) BotUtils.getRandomFromList(hobbies, random));
            players.get(i).setHealth((Health) BotUtils.getRandomFromList(healths, random));
            if( (random.nextInt(100) >= 45 || (i == (players.size()-1) && isNoOneHasScripts())) && !this.actionCards.isEmpty() ) {
                Class<? extends ActionCard> asc = (Class<? extends ActionCard>) BotUtils.getRandomFromList(this.actionCards, random);
                this.actionCards.removeIf(p -> p.getCanonicalName().equals(asc.getCanonicalName()));
                players.get(i).setScripts(new ArrayList<>(Collections.singletonList(asc)));
            } else {
                players.get(i).setScripts(new ArrayList<>());
            }
        }
        doNight();
    }

    private void doNight() {
        this.dayNightFields.setIsNight(true);
        this.dayNightFields.setNightToken(random.nextInt(1000)+10);
        this.dayNightFields.setPoll(new HashMap<>());
        this.dayNightFields.setTurnCount(this.dayNightFields.getTurnCount()+1);
        for( Player p : players ) {
            SendMessage sendMessage = new SendMessage(p.getTelegramId()+"", BotAccountProfileGenerator.build(textDataValService, p));
            sendApi(sendMessage);
            sendMessage = new SendMessage(p.getTelegramId()+"", Constants.SHOW_TIME);
            sendMessage.setReplyMarkup(BotUtils.getShowKeyboard(p.getInfoSections()));
            sendApi(sendMessage);
            if( p.getScripts().isEmpty() ) continue;
            sendMessage = new SendMessage(p.getTelegramId()+"", Constants.SCRIPT_MESSAGE);
            sendMessage.setReplyMarkup(BotUtils.getScriptKeyboard(p.getScripts()));
            try {
                setScriptMessageId(p, sendApiMethod(sendMessage).getMessageId());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void setScriptMessageId(Player p, Integer messageId) {
        IntStream.range(0, players.size())
                .boxed()
                .filter(i -> players.get(i).getTelegramId().longValue() == p.getTelegramId().longValue())
                .forEach(i -> players.get(i).setScriptMessageId(messageId));
    }

    private String getStringById(Long id) {
        return textDataValService.getTextDataValById(id).getText();
    }

    private void processNightScriptButton(Player p, CallbackQuery callbackQuery, String data) {
        Class<? extends ActionCard> script = p.getScripts().stream()
                .filter(s -> {
                            try {
                                return HashUtils.getCRC32(s.getDeclaredConstructor().newInstance().getName().getBytes()).equals(data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                )
                .findFirst()
                .orElse(null);
        if( script == null ) return;
        if( p.getScriptMessageId() != null ) sendApi(new DeleteMessage(callbackQuery.getMessage().getChatId()+"", p.getScriptMessageId()));
        sendApi(new SendMessage(groupId, String.format(Constants.PRESSED_SCRIPT_NIGHT, callbackQuery.getFrom().getFirstName())));
        sendApi(new SendMessage(callbackQuery.getMessage().getChatId()+"", Constants.THANK_YOU));
        p.setScripts(new ArrayList<>());
        try {
            script.getDeclaredConstructor(BunkerBot.class, Player.class).newInstance(this, p).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processNightButton(CallbackQuery callbackQuery) {
        if( this.hasPlayerWithId(callbackQuery.getFrom().getId()) &&
                !getPlayerById(callbackQuery.getFrom().getId()).getIsAnswered() ) {
            Player p = getPlayerById(callbackQuery.getFrom().getId());
            InfoSections ins = p.getInfoSections();
            SectionType curSection = SectionType.fromHash(callbackQuery.getData());
            switch(curSection) {
                case GENDER:
                    dayNightFields.appendMessage(String.format(Constants.GENDER_MESAGE, callbackQuery.getFrom().getFirstName(),  getStringById(p.getGender().getGenderTextId()),
                            p.getGender().getCanDie() ? Constants.TRUE : Constants.FALSE,
                            p.getGender().getIsMale() ? Constants.TRUE : Constants.FALSE,
                            p.getGender().getIsFemale() ? Constants.TRUE : Constants.FALSE) + "\n");
                    break;
                case HEALTH:
                    dayNightFields.appendMessage(String.format(Constants.HEALTH_MESSAGE, callbackQuery.getFrom().getFirstName(), getStringById(p.getHealth().getTextNameId()),
                            getStringById(p.getHealth().getTextDescId()),
                            (int) (p.getHealth().getHealth_index()*100f),
                            p.getHealth().getIsChildfree() ? Constants.TRUE : Constants.FALSE) + "\n");
                    break;
                case AGE:
                    dayNightFields.appendMessage(String.format(Constants.AGE_MESSAGE, callbackQuery.getFrom().getFirstName(), p.getAge()) + "\n");
                    break;
                case HOBBY:
                    dayNightFields.appendMessage(String.format(Constants.HOBBY_MESSAGE, callbackQuery.getFrom().getFirstName(),
                            getStringById(p.getHobby().getTextDescId())) + "\n");
                    break;
                case LUGGAGE:
                    dayNightFields.appendMessage(String.format(Constants.LUGG_MESSAGE, callbackQuery.getFrom().getFirstName(),
                            getStringById(p.getLuggage().getTextNameId()),
                            getStringById(p.getLuggage().getTextDescId())) + "\n");
                    break;
                case WORK:
                    dayNightFields.appendMessage(String.format(Constants.WORK_MESSAGE, callbackQuery.getFrom().getFirstName(),
                            getStringById(p.getWork().getTextNameId()),
                            getStringById(p.getWork().getTextDescId())) + "\n");
                    break;
                default:
                    processNightScriptButton(p, callbackQuery, callbackQuery.getData());
                    return;
            }
            ins.pushShowedState(curSection);
            setIsAnswered(callbackQuery.getFrom().getId());
            updateInfoSections(p, ins);
            sendApi(new SendMessage(callbackQuery.getMessage().getChatId()+"", Constants.THANK_YOU));
            sendApi(new DeleteMessage(callbackQuery.getMessage().getChatId()+"", callbackQuery.getMessage().getMessageId()));
            if( p.getScriptMessageId() != null) sendApi(new DeleteMessage(callbackQuery.getMessage().getChatId()+"", p.getScriptMessageId()));
            sendApi(new SendMessage(groupId, String.format(Constants.PRESSED_NIGHT, callbackQuery.getFrom().getFirstName())));
            if( !isAllAnswered() ) return;
            if( dayNightFields.getTurnCount() >= 3 ) {
                doDay();
                return;
            }
            setAllNotAnswered();
            doNight();
        }
    }
    
    public void sendApi(BotApiMethod<? extends Serializable> method) {
        this.linkedQueue.add(method);
    }

    private void updateInfoSections(Player p1, InfoSections infoSections) {
        players.stream().filter((p) -> p.equals(p1)).forEach((p) -> p.setInfoSections(infoSections));
    }

    private void setIsAnswered(Long id) {
        players.stream().filter(p -> p.getTelegramId().equals(id) ).forEach(p -> p.setIsAnswered(true));
    }

    private void setIsVoted(Long id) {
        players.stream().filter(p -> p.getTelegramId().equals(id) ).forEach(p -> p.setIsVoted(true));
    }

    private void doDay() {
        dayNightFields.setIsNight(false);
        double p = Math.floor(this.liveFormula.calc()*100d);
        if( this.last_p < 0 ) { this.last_p = p; }
        if( p > this.last_p ) {
            sendApi(new SendMessage(groupId, String.format(Constants.DAY_MESSAGE_UPPER, (int) p, (p - this.last_p))));
        } else if( p < this.last_p ) {
            sendApi(new SendMessage(groupId, String.format(Constants.DAY_MESSAGE_DOWN, (int) p, (this.last_p - p))));
        } else {
            sendApi(new SendMessage(groupId, String.format(Constants.DAY_MESSAGE, p)));
        }
        this.last_p = p;
        sendApi(new SendMessage(groupId, dayNightFields.getDayMessage()));
        dayNightFields.setDayMessage("");
        setAllNotAnswered();
        setAllNotVoted();
        SendPoll sp = new SendPoll(groupId, Constants.POLL_QUESTION, getAllUsers());
        sp.setIsAnonymous(false);
        sp.setAllowMultipleAnswers(false);
        try {
            dayNightFields.setLastPollId(sendApiMethod(sp).getPoll().getId());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void setAllNotVoted() {
        players.forEach((p) -> p.setIsVoted(false));
    }

    private Boolean isAllVoted() {
        return players.stream().allMatch(Player::getIsVoted);
    }

    private List<String> getAllUsers() {
        return players.stream().map(Player::getFirstName).collect(Collectors.toList());
    }

    private void setAllNotAnswered() {
        players.forEach((p) -> p.setIsAnswered(false));
    }

    private Boolean isAllAnswered() {
        return players.stream().allMatch(Player::getIsAnswered);
    }

    private void showInfo() {
        if( this.dayNightFields.getIsNight() ) {
            sendApi(new SendMessage(groupId, Constants.CANT_SEND_NOT_DAY));
            return;
        }
        StringBuilder message = new StringBuilder();
        message.append(Constants.INFO_MESSAGE);
        for( Player p : players ) {
            message.append(p.getFirstName());
            message.append(":\n");
            InfoSections s = p.getInfoSections();
            s.getSections().forEach(s1 -> {
                switch (s1.getType()) {
                    case GENDER -> message.append(String.format(Constants.GENDER_MESAGE, p.getFirstName(),  getStringById(p.getGender().getGenderTextId()),
                            p.getGender().getCanDie() ? Constants.TRUE : Constants.FALSE,
                            p.getGender().getIsMale() ? Constants.TRUE : Constants.FALSE,
                            p.getGender().getIsFemale() ? Constants.TRUE : Constants.FALSE));
                    case AGE -> message.append(String.format(Constants.AGE_MESSAGE, p.getFirstName(), p.getAge()));
                    case LUGGAGE -> message.append(String.format(Constants.LUGG_MESSAGE, p.getFirstName(),
                            getStringById(p.getLuggage().getTextNameId()),
                            getStringById(p.getLuggage().getTextDescId())));
                    case HEALTH -> message.append(String.format(Constants.HEALTH_MESSAGE, p.getFirstName(), getStringById(p.getHealth().getTextNameId()),
                            getStringById(p.getHealth().getTextDescId()),
                            (int) (p.getHealth().getHealth_index()*100f),
                            p.getHealth().getIsChildfree() ? Constants.TRUE : Constants.FALSE));
                    case WORK -> message.append(String.format(Constants.WORK_MESSAGE, p.getFirstName(),
                            getStringById(p.getWork().getTextNameId()),
                            getStringById(p.getWork().getTextDescId())));
                    case HOBBY -> message.append(String.format(Constants.HOBBY_MESSAGE, p.getFirstName(),
                            getStringById(p.getHobby().getTextDescId())));
                }
                message.append("\n");
            });
            message.append("\n");
        }
        sendApi(new SendMessage(groupId, message.toString()));
    }

    private void endVote() {
        Integer max = dayNightFields.getPoll().values().stream().max(Integer::compareTo).orElse(0);
        long count = dayNightFields.getPoll().values().stream().filter(p -> p.equals(max)).count();
        SendMessage sendMessage = new SendMessage(groupId, Constants.ENDVOTE);
        if( count > 1 ) {
            sendMessage = new SendMessage(groupId, Constants.DRAW);
        } else {
            removeVotePlayers(max);
        }
        sendApi(sendMessage);
        if( !checkEndGame() ) {
            doNight();
            return;
        }
        endGame();
    }

    private Boolean checkEndGame() {
        return players.size() < 2 || dayNightFields.getTurnCount() >= 6;
    }

    private void endGame() {
        double d = Math.floor(this.liveFormula.calc()*100d);
        sendApi(new SendMessage(groupId, String.format(Constants.END_GAME, d)));
        if(!players.isEmpty() && Math.floor(random.nextDouble()*100d) <= d) {
            sendApi(new SendMessage(groupId, String.format(Constants.WIN_MESSAGE,
                    players.stream().map(Player::getFirstName).collect(Collectors.joining(", "))
                    )));
        } else {
            sendApi(new SendMessage(groupId, Constants.LOSE_MESSAGE));
        }
        interruptGame();
    }

    private void removeVotePlayers(Integer max) {
        dayNightFields.getPoll()
                .entrySet()
                .stream()
                .filter(e -> e.getValue().equals(max))
                .forEach(i -> {
                    sendApi(new SendMessage(groupId, String.format(Constants.REMOVE_PLAYER, players.get(i.getKey()).getFirstName())));
                    players.remove(i.getKey().intValue());
                });
    }

    private void interruptGame() {
        this.dayNightFields = new DayNightFields();
        this.players = new ArrayList<>();
        this.gameState = GameState.NONE;
        this.last_p = 0;
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if( update.hasPollAnswer() && update.getPollAnswer().getPollId().equals(dayNightFields.getLastPollId())
                && !dayNightFields.getIsNight() && !update.getPollAnswer().getOptionIds().isEmpty()) {
            if( getPlayerById(update.getPollAnswer().getUser().getId()).getIsVoted() )
                return;
            if( dayNightFields.getPoll().containsKey(update.getPollAnswer().getOptionIds().get(0)) ) {
                dayNightFields.getPoll().put(update.getPollAnswer().getOptionIds().get(0),
                        dayNightFields.getPoll().get(update.getPollAnswer().getOptionIds().get(0)) + 1);
            } else {
                dayNightFields.getPoll().put(update.getPollAnswer().getOptionIds().get(0), 1);
            }
            setIsVoted(update.getPollAnswer().getUser().getId());
            sendApi(new SendMessage(groupId, String.format(Constants.USER_VOTED, update.getPollAnswer().getUser().getFirstName())));
            if( isAllVoted() )
                endVote();
            return;
        }

        if( update.hasCallbackQuery() ) {
            sendApi(
                    new AnswerCallbackQuery(
                            update.getCallbackQuery().getId()
                    )
            );
            if( update.getCallbackQuery().getData().equals(HashUtils.getCRC32(Constants.JOIN_GAME_BTN.getBytes())) ) {
                joinGame(update.getCallbackQuery().getFrom(), 0);
            } else if( update.getCallbackQuery().getData().equals(HashUtils.getCRC32(Constants.START_GAME_BTN.getBytes())) ) {
                if( isAdmin(update.getCallbackQuery().getFrom()) ) {
                    startGame();
                    return;
                }
                sendApi(new SendMessage(groupId, Constants.NOT_ADMIN_EXCEPTION));
            } else if( gameState == GameState.STARTED && dayNightFields.getIsNight() ) {
                processNightButton(update.getCallbackQuery());
            }
            return;
        }

        if( !(update.hasMessage() && update.getMessage().hasText() && update.getMessage().isCommand()) ) return;

        String chatId = update.getMessage().getChatId()+"";

        // TODO: state-based refactoring (reduce IF count)
        if( ( update.getMessage().getText().equals(Commands.SET_GROUP + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.SET_GROUP)) &&
                update.getMessage().getFrom().getId().equals(getMasterId()) && gameState == GameState.NONE) {
            groupId = chatId;
            sendApi(new SendMessage(chatId, Constants.GROUP_SET));
        }

        if( !chatId.equals(groupId) ) return;

        // TODO: state-based refactoring (reduce IF count)
        if ( (update.getMessage().getText().equals(Commands.START_GAME + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.START_GAME) ) &&
                gameState == GameState.NONE) {
            SendMessage message = new SendMessage(chatId, Constants.START_GAME_MSG);
            message.setReplyMarkup(BotUtils.getJoinKeyboard());
            sendApi(message);
            gameState = GameState.JOINING;
        } else if (update.getMessage().getText().equals(Commands.JOIN_GAME + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.JOIN_GAME)) {
            joinGame(update.getMessage().getFrom(), update.getMessage().getMessageId());
        } else if (update.getMessage().getText().equals(Commands.HELP + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.HELP)) {
            sendApi(new SendMessage(chatId, Constants.HELP));
        } else if ((update.getMessage().getText().equals(Commands.STOP_GAME + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.STOP_GAME) ) &&
                gameState != GameState.NONE) {
            if( isAdmin(update.getMessage().getFrom()) ) {
                sendApi(new SendMessage(groupId, Constants.INTERRUPT_GAME));
                interruptGame();
                return;
            }
            sendApi(new SendMessage(groupId, Constants.NOT_ADMIN_EXCEPTION));
        } else if((update.getMessage().getText().equals(Commands.GET_INFO + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.GET_INFO) ) &&
                gameState == GameState.STARTED) {
            showInfo();
        }
    }

    private Player getPlayerById(Long id) {
        return players.stream().filter((p) -> p.getTelegramId().equals(id) ).findFirst().orElse(null);
    }

    private Boolean hasPlayerWithId(Long id) {
        return players.stream().anyMatch((p) -> p.getTelegramId().equals(id));
    }
}