package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;

public class GodsWillCard extends ActionCard {
    public GodsWillCard(BunkerBot bot, Player activator) {
        super(bot, activator);
    }

    public GodsWillCard() {
    }


    @Override
    public void execute() {
        this.bot.liveFormula.add(5.0d / 100.0d);
    }

    @Override
    public String getName() {
        return Constants.GODS_WILL_CARD;
    }
}
