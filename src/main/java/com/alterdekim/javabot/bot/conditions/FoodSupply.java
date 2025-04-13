package com.alterdekim.javabot.bot.conditions;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.components.BunkerBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class FoodSupply implements ConditionCard {
    @Override
    public void executeCard(BunkerBot bot, String chatId) {
        bot.liveFormula.add(5.0d / 100.0d);
        bot.sendApi(new SendMessage(chatId, Constants.FOOD_SUPPLY_COND));
    }
}
