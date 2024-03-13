package com.alterdekim.javabot.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class InfoSections {
    private Boolean isGenderShowed;
    private Boolean isAgeShowed;
    private Boolean isWorkShowed;
    private Boolean isLuggageShowed;
    private Boolean isHobbyShowed;
    private Boolean isHealthShowed;

    public InfoSections() {
        this.isAgeShowed = false;
        this.isLuggageShowed = false;
        this.isGenderShowed = false;
        this.isWorkShowed = false;
        this.isHealthShowed = false;
        this.isHobbyShowed = false;
    }
}
