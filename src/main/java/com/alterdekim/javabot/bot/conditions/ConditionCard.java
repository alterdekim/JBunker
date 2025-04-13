package com.alterdekim.javabot.bot.conditions;


import com.alterdekim.javabot.components.BunkerBot;

public interface ConditionCard {
    void executeCard(BunkerBot bot, String chatId);
}
