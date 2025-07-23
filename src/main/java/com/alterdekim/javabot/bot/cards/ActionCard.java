package com.alterdekim.javabot.bot.cards;

import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.components.BunkerBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
public abstract class ActionCard {
    BunkerBot bot;
    Player activator;

    public abstract void execute();

    public abstract String getName();

    List<Player> getPlayersListWithoutActivator() {
        return this.bot.players
                .stream()
                .filter(p -> p.getTelegramId().longValue() != this.activator.getTelegramId().longValue())
                .collect(Collectors.toList());
    }
}
