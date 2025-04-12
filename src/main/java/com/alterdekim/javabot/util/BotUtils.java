package com.alterdekim.javabot.util;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.InfoSections;
import com.alterdekim.javabot.bot.SectionType;
import com.alterdekim.javabot.bot.cards.ActionCard;
import com.alterdekim.javabot.components.RandomComponent;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class BotUtils {
    public static InlineKeyboardMarkup getJoinKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> columns = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(Constants.JOIN_GAME_BTN);
        inlineKeyboardButton.setCallbackData(HashUtils.getCRC32(Constants.JOIN_GAME_BTN.getBytes()));
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton);
        columns.add(keyboardButtonsRow1);
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(Constants.START_GAME_BTN);
        inlineKeyboardButton1.setCallbackData(HashUtils.getCRC32(Constants.START_GAME_BTN.getBytes()));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardButton1);
        columns.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(columns);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getShowKeyboard(InfoSections infoSections) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> columns = new ArrayList<>();
        List<SectionType> sectionTypes = new ArrayList<>(Arrays.asList(SectionType.values()));
        infoSections.getSections().forEach(s -> sectionTypes.remove(s.getType()));
        sectionTypes.forEach(s -> {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            switch(s) {
                case HOBBY:
                    inlineKeyboardButton.setText(Constants.HOBBY_BTN);
                    inlineKeyboardButton.setCallbackData(HashUtils.getCRC32(Constants.HOBBY_BTN.getBytes()));
                    columns.add(Collections.singletonList(inlineKeyboardButton));
                    break;
                case AGE:
                    inlineKeyboardButton.setText(Constants.AGE_BTN);
                    inlineKeyboardButton.setCallbackData(HashUtils.getCRC32(Constants.AGE_BTN.getBytes()));
                    columns.add(Collections.singletonList(inlineKeyboardButton));
                    break;
                case GENDER:
                    inlineKeyboardButton.setText(Constants.GENDER_BTN);
                    inlineKeyboardButton.setCallbackData(HashUtils.getCRC32(Constants.GENDER_BTN.getBytes()));
                    columns.add(Collections.singletonList(inlineKeyboardButton));
                    break;
                case HEALTH:
                    inlineKeyboardButton.setText(Constants.HEALTH_BTN);
                    inlineKeyboardButton.setCallbackData(HashUtils.getCRC32(Constants.HEALTH_BTN.getBytes()));
                    columns.add(Collections.singletonList(inlineKeyboardButton));
                    break;
                case WORK:
                    inlineKeyboardButton.setText(Constants.WORK_BTN);
                    inlineKeyboardButton.setCallbackData(HashUtils.getCRC32(Constants.WORK_BTN.getBytes()));
                    columns.add(Collections.singletonList(inlineKeyboardButton));
                    break;
                case LUGGAGE:
                    inlineKeyboardButton.setText(Constants.LUGG_BTN);
                    inlineKeyboardButton.setCallbackData(HashUtils.getCRC32(Constants.LUGG_BTN.getBytes()));
                    columns.add(Collections.singletonList(inlineKeyboardButton));
                    break;
            }
        });
        inlineKeyboardMarkup.setKeyboard(columns);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getScriptKeyboard(List<Class<? extends ActionCard>> scripts) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(scripts.stream()
                .map(s -> {
                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                    try {
                        inlineKeyboardButton.setText(s.getDeclaredConstructor().newInstance().getName());
                        inlineKeyboardButton.setCallbackData(HashUtils.getCRC32(s.getDeclaredConstructor().newInstance().getName().getBytes()));
                    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                             InvocationTargetException e) {
                        log.error(e.getMessage());
                    }
                    return Collections.singletonList(inlineKeyboardButton);
                }).collect(Collectors.toList()));
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getGameTypeKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> columns = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(Constants.PROBABILITY);
        inlineKeyboardButton.setCallbackData(HashUtils.getCRC32(Constants.PROBABILITY.getBytes()));
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton);
        columns.add(keyboardButtonsRow1);
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(Constants.DEATHMATCH);
        inlineKeyboardButton1.setCallbackData(HashUtils.getCRC32(Constants.DEATHMATCH.getBytes()));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardButton1);
        columns.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(columns);
        return inlineKeyboardMarkup;
    }

    public static String mentionUser(String name) {
        return " @"+name+" ";
    }

    public static Object getRandomFromList(List list, RandomComponent random) {
        return list.get(random.nextInt(list.size()));
    }
}
