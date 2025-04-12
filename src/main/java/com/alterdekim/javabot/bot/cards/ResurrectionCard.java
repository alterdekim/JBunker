package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;
import com.alterdekim.javabot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ResurrectionCard extends ActionCard {

    public ResurrectionCard(BunkerBot bot, Player activator) {
        super(bot, activator);
    }

    public ResurrectionCard() {
    }

    @Override
    public void execute() {
        Player p = (Player) BotUtils.getRandomFromList(this.bot.dead_players, this.bot.random);
        p.setIsAnswered(true);
        this.bot.players.add(p);
        this.bot.sendApi(new SendMessage(this.activator.getTelegramId()+"", String.format(Constants.RESURRECTION_CARD_TRIGGERED, p.getFirstName())));
    }

    @Override
    public String getName() {
        return Constants.RESURRECTION_CARD;
    }
}
