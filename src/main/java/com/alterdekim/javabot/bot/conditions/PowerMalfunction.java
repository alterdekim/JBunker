package com.alterdekim.javabot.bot.conditions;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.components.BunkerBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class PowerMalfunction implements ConditionCard {
    @Override
    public void executeCard(BunkerBot bot) {
        bot.liveFormula.sub(5.0d / 100.0d);
        bot.sendApi(bot.newMessage(Constants.POWER_MALFUNCTION));
    }
}
