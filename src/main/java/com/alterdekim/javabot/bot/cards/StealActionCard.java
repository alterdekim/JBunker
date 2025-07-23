package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;
import com.alterdekim.javabot.entities.Luggage;
import com.alterdekim.javabot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StealActionCard extends ActionCard {

    private static final long NOTHING_ID = 7;

    public StealActionCard(BunkerBot bot, Player activator) {
        super(bot, activator);
    }

    public StealActionCard() {
    }

    @Override
    public void execute() {
        Player p = (Player) BotUtils.getRandomFromList(this.getPlayersListWithoutActivator(), this.bot.random);
        Luggage l = p.getLuggage();
        p.setLuggage(this.bot.luggageService.getLuggageById(NOTHING_ID));
        this.activator.setLuggage(l);
        this.bot.sendApi(new SendMessage(this.activator.getTelegramId()+"", String.format(Constants.STEAL_LUGGAGE_TRIGGERED, p.getFirstName())));
    }

    @Override
    public String getName() {
        return Constants.STEAL_LUGGAGE_CARD;
    }
}
