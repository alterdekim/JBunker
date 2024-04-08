package com.alterdekim.javabot.bot;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.util.HashUtils;

public enum BioButtons {
    HOBBY(Constants.HOBBY_BTN),
    WORK(Constants.WORK_BTN),
    HEALTH(Constants.HEALTH_BTN),
    AGE(Constants.AGE_BTN),
    GENDER(Constants.GENDER_BTN),
    LUGGAGE(Constants.LUGG_BTN),
    UNKNOWN("");

    private final String name;

    BioButtons(String name) {
        this.name = name;
    }

    public String getHash() {
        return HashUtils.getCRC32(this.name.getBytes());
    }

    public static BioButtons fromHash( String hash ) {
        for( BioButtons b : values() ) {
            if( b.getHash().equals(hash) ) return b;
        }
        return UNKNOWN;
    }
}
