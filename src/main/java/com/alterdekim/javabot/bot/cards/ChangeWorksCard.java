package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;
import com.alterdekim.javabot.entities.Work;
import com.alterdekim.javabot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ChangeWorksCard extends ActionCard {

    public ChangeWorksCard(BunkerBot bot, Player activator) {
        super(bot, activator);
    }

    @Override
    public void execute() {
        Player p = (Player) BotUtils.getRandomFromList(this.bot.players, this.bot.random);
        Work w = this.activator.getWork();
        this.activator.setWork(p.getWork());
        p.setWork(w);
        this.bot.sendApi(new SendMessage(this.activator.getTelegramId()+"", String.format(Constants.CHANGE_WORKS_TRIGGERED, p.getFirstName())));
    }

    @Override
    public String getName() {
        return Constants.CHANGE_WORKS;
    }
}
