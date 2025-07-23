package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;
import com.alterdekim.javabot.entities.Hobby;
import com.alterdekim.javabot.entities.Luggage;
import com.alterdekim.javabot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ChangeLuggageCard extends ActionCard {

    public ChangeLuggageCard(BunkerBot bot, Player activator) {
        super(bot, activator);
    }

    public ChangeLuggageCard() {
    }

    @Override
    public void execute() {
        Player p = (Player) BotUtils.getRandomFromList(this.getPlayersListWithoutActivator(), this.bot.random);
        Luggage w = this.activator.getLuggage();
        this.activator.setLuggage(p.getLuggage());
        p.setLuggage(w);
        this.bot.sendApi(new SendMessage(this.activator.getTelegramId()+"", String.format(Constants.CHANGE_LUGGAGE_TRIGGERED, p.getFirstName())));
    }

    @Override
    public String getName() {
        return Constants.CHANGE_LUGGAGE_CARD;
    }
}
