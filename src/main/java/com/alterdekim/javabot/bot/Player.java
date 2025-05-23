package com.alterdekim.javabot.bot;

import com.alterdekim.javabot.bot.cards.ActionCard;
import com.alterdekim.javabot.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Player {
    private Long telegramId;
    private int age;
    private Bio gender;
    private Health health;
    private Hobby hobby;
    private Work work;
    private Luggage luggage;
    private Boolean isAnswered = false;
    private String firstName;
    private InfoSections infoSections;
    private Boolean isVoted = false;
    private List<Class<? extends ActionCard>> scripts;
    private Integer scriptMessageId;

    public Player(Long telegramId, String name) {
        this.telegramId = telegramId;
        this.firstName = name;
        this.infoSections = new InfoSections();
        this.scripts = new ArrayList<>();
    }
}
