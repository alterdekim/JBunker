package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;
import com.alterdekim.javabot.entities.Hobby;
import com.alterdekim.javabot.entities.Work;
import com.alterdekim.javabot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ChangeHobbyCard extends ActionCard {
    public ChangeHobbyCard(BunkerBot bot, Player activator) {
        super(bot, activator);
    }

    public ChangeHobbyCard() {
    }

    @Override
    public void execute() {
        Player p = (Player) BotUtils.getRandomFromList(this.getPlayersListWithoutActivator(), this.bot.random);
        Hobby w = this.activator.getHobby();
        this.activator.setHobby(p.getHobby());
        p.setHobby(w);
        this.bot.sendApi(new SendMessage(this.activator.getTelegramId()+"", String.format(Constants.CHANGE_HOBBY_TRIGGERED, p.getFirstName())));
    }

    @Override
    public String getName() {
        return Constants.CHANGE_HOBBY_CARD;
    }
}
