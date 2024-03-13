package com.alterdekim.javabot.bot;

import com.alterdekim.javabot.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<ActionScript> scripts;
    private Integer scriptMessageId;

    public Player(Long telegramId, String name) {
        this.telegramId = telegramId;
        this.firstName = name;
        this.infoSections = new InfoSections();
    }
}
