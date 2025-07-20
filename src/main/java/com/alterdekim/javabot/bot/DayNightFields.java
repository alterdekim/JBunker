package com.alterdekim.javabot.bot;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DayNightFields {
    private Boolean isNight;
    private Integer nightToken;
    private String lastPollId;
    private Map<Integer, Integer> poll;
    private Map<Long, SectionType> dayMessage;
    private Integer turnCount;

    public DayNightFields() {
        this.isNight = false;
        this.nightToken = 0;
        this.lastPollId = "";
        this.poll = new HashMap<>();
        this.dayMessage = new HashMap<>();
        this.turnCount = 0;
    }

    public void appendMessage(Long playerId, SectionType sectionType) {
        this.dayMessage.put(playerId, sectionType);
    }
}
