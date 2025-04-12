package com.alterdekim.javabot.bot.cards;


import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;

public class Sabotage extends ActionCard {

    public Sabotage(BunkerBot bot, Player activator) {
        super(bot, activator);
    }

    public Sabotage() {
    }

    @Override
    public void execute() {

    }

    @Override
    public String getName() {
        return Constants.SABOTAGE_CARD;
    }
}
