package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.bot.SectionType;
import com.alterdekim.javabot.components.BunkerBot;
import com.alterdekim.javabot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ScannerCard extends ActionCard {

    public ScannerCard(BunkerBot bot, Player activator) {
        super(bot, activator);
    }

    public ScannerCard() {
    }

    @Override
    public void execute() {
        Player p = (Player) BotUtils.getRandomFromList(this.getPlayersListWithoutActivator(), this.bot.random);
        for(SectionType type : SectionType.values() ) {
            if( !p.getInfoSections().isShowed(type) ) {
                String ss = switch (type) {
                    case AGE -> Constants.AGE_BTN + ": " + p.getAge();
                    case WORK -> Constants.WORK_BTN + ": " + this.bot.textDataValService.getTextDataValById(p.getWork().getTextNameId()).getText();
                    case HOBBY -> Constants.HOBBY_BTN + ": " + this.bot.textDataValService.getTextDataValById(p.getHobby().getTextDescId()).getText();
                    case GENDER -> Constants.GENDER_BTN + ": " + this.bot.textDataValService.getTextDataValById(p.getGender().getGenderTextId()).getText();
                    case HEALTH -> Constants.HEALTH_BTN + ": " + this.bot.textDataValService.getTextDataValById(p.getHealth().getTextNameId()).getText();
                    case LUGGAGE -> Constants.HEALTH_BTN + ": " + this.bot.textDataValService.getTextDataValById(p.getLuggage().getTextNameId()).getText();
                    default -> Constants.FALSE;
                };
                this.bot.sendApi(new SendMessage(this.activator.getTelegramId()+"", String.format(Constants.SCANNER_TRIGGERED, p.getFirstName(), ss)));
                break;
            }
        }
    }

    @Override
    public String getName() {
        return Constants.SCANNER_CARD;
    }
}
