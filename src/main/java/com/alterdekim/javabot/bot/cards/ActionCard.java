package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public abstract class ActionCard {
    BunkerBot bot;
    Player activator;

    public abstract void execute();

    public abstract String getName();
}
