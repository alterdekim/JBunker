package com.alterdekim.javabot.bot;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.util.HashUtils;

public enum SectionType {
    GENDER(Constants.GENDER_BTN, true), // Also called "bio" in some cases
    HEALTH(Constants.HEALTH_BTN, true),
    HOBBY(Constants.HOBBY_BTN, true),
    LUGGAGE(Constants.LUGG_BTN, true),
    WORK(Constants.WORK_BTN, true),
    AGE(Constants.AGE_BTN, false),
    UNKNOWN("", false);

    private final String name;
    private final Boolean dbAppearance;

    SectionType(String name, Boolean dbAppearance) {
        this.name = name;
        this.dbAppearance = dbAppearance;
    }

    public String getHash() {
        return HashUtils.getCRC32(this.name.getBytes());
    }

    public static SectionType fromHash( String hash ) {
        for( SectionType b : values() ) {
            if( b.getHash().equals(hash) ) return b;
        }
        return UNKNOWN;
    }
}
