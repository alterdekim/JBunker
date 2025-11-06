package com.alterdekim.javabot.bot.conditions;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.components.BunkerBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AirStuff implements ConditionCard {
    @Override
    public void executeCard(BunkerBot bot) {
        bot.sendApi(bot.newMessage(Constants.AIR_COND));
    }
}
