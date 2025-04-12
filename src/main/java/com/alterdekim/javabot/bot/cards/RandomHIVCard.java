package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class RandomHIVCard extends ActionCard {
    private static final long HIV_ID = 31;

    @Override
    public void execute() {
        Player p = (Player) BotUtils.getRandomFromList(this.bot.players, this.bot.random);
        p.setHealth(this.bot.healthService.getHealthById(HIV_ID));
        this.bot.sendApi(new SendMessage(this.activator.getTelegramId()+"", String.format(Constants.RANDOM_HIV_ENABLED, p.getFirstName())));
    }

    @Override
    public String getName() {
        return Constants.RANDOM_HIV;
    }
}
