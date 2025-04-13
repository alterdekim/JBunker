package com.alterdekim.javabot.bot.conditions;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.components.BunkerBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AIStuff implements ConditionCard {
    @Override
    public void executeCard(BunkerBot bot, String chatId) {
        bot.sendApi(new SendMessage(chatId, Constants.AI_STUFF_COND));
    }
}
