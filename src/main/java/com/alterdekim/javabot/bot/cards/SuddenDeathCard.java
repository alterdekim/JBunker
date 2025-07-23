package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;
import com.alterdekim.javabot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SuddenDeathCard extends ActionCard {
    public SuddenDeathCard(BunkerBot bot, Player activator) {
        super(bot, activator);
    }

    public SuddenDeathCard() {
    }

    @Override
    public void execute() {
        Player p = (Player) BotUtils.getRandomFromList(this.bot.players, this.bot.random);
        p.setIsAnswered(true);
        this.bot.dead_players.add(p);
        this.bot.players.remove(p);
        this.bot.sendApi(new SendMessage(this.activator.getTelegramId()+"", String.format(Constants.SUDDEN_DEATH_TRIGGERED, p.getFirstName())));
    }

    @Override
    public String getName() {
        return Constants.SUDDEN_DEATH;
    }
}
