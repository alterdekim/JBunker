package com.alterdekim.javabot.util;

import com.alterdekim.javabot.bot.InfoSections;
import com.alterdekim.javabot.bot.Player;
import com.alterdekim.javabot.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class LuaDeserializer {
    public static List<Player> deserializePlayers(LuaValue val) {
        LuaTable table = val.checktable();
        return Arrays.stream(table.keys())
                .map(key -> deserializePlayer(table.get(key).checktable()))
                .collect(Collectors.toList());
    }

    private static Player deserializePlayer(LuaTable table) {
        int age = table.get("age").checkint();
        Player p = new Player(table.get("telegramId").checklong(), table.get("name").checkjstring());
        p.setAge(age);
        p.setIsAnswered(table.get("isAnswered").checkboolean());
        p.setFirstName(table.get("firstName").checkjstring());
        p.setIsVoted(table.get("isVoted").checkboolean());
        p.setScriptMessageId(table.get("scriptMessageId").checkint());

        p.setGender(deserializeGender(table.get("gender").checktable()));
        p.setHealth(deserializeHealth(table.get("health").checktable()));
        p.setHobby(deserializeHobby(table.get("hobby").checktable()));
        p.setWork(deserializeWork(table.get("work").checktable()));
        p.setLuggage(deserializeLuggage(table.get("luggage").checktable()));

        p.setInfoSections(deserializeInfoSections(table.get("infoSections").checktable()));
        return p;
    }

    private static InfoSections deserializeInfoSections(LuaTable table) {
        InfoSections infoSections = new InfoSections();
        infoSections.setIsGenderShowed(table.get("isGenderShowed").checkboolean());
        infoSections.setIsAgeShowed(table.get("isAgeShowed").checkboolean());
        infoSections.setIsWorkShowed(table.get("isWorkShowed").checkboolean());
        infoSections.setIsLuggageShowed(table.get("isLuggageShowed").checkboolean());
        infoSections.setIsHobbyShowed(table.get("isHobbyShowed").checkboolean());
        infoSections.setIsHealthShowed(table.get("isHealthShowed").checkboolean());
        return infoSections;
    }

    private static Hobby deserializeHobby(LuaTable table) {
        Hobby hobby = new Hobby();
        hobby.setId(table.get("id").checklong());
        hobby.setTextDescId(table.get("textDescId").checklong());
        hobby.setAsocial(table.get("asocial").tofloat());
        hobby.setPower(table.get("power").tofloat());
        hobby.setViolence(table.get("violence").tofloat());
        hobby.setFoodstuffs(table.get("foodstuffs").tofloat());
        return hobby;
    }

    private static Work deserializeWork(LuaTable table) {
        Work work = new Work();
        work.setId(table.get("id").checklong());
        work.setTextDescId(table.get("textDescId").checklong());
        work.setTextNameId(table.get("textNameId").checklong());
        work.setAsocial(table.get("asocial").tofloat());
        work.setPower(table.get("power").tofloat());
        work.setViolence(table.get("violence").tofloat());
        work.setFoodstuffs(table.get("foodstuffs").tofloat());
        return work;
    }

    private static Luggage deserializeLuggage(LuaTable table) {
        Luggage luggage = new Luggage();
        luggage.setId(table.get("id").checklong());
        luggage.setTextDescId(table.get("textDescId").checklong());
        luggage.setTextNameId(table.get("textNameId").checklong());
        luggage.setAsocial(table.get("asocial").tofloat());
        luggage.setPower(table.get("power").tofloat());
        luggage.setViolence(table.get("violence").tofloat());
        luggage.setFoodstuffs(table.get("foodstuffs").tofloat());
        luggage.setGarbage(table.get("garbage").checkboolean());
        return luggage;
    }

    private static Health deserializeHealth(LuaTable table) {
        Health health = new Health();
        health.setId(table.get("id").checklong());
        health.setHealth_index(table.get("health_index").tofloat());
        health.setIsChildfree(table.get("isChildfree").checkboolean());
        health.setTextDescId(table.get("textDescId").checklong());
        health.setTextNameId(table.get("textNameId").checklong());
        return health;
    }

    private static Bio deserializeGender(LuaTable table) {
        Bio bio = new Bio();
        bio.setId(table.get("id").checklong());
        bio.setGenderTextId(table.get("genderTextId").checklong());
        bio.setCanDie(table.get("canDie").checkboolean());
        bio.setIsMale(table.get("isMale").checkboolean());
        bio.setIsFemale(table.get("isFemale").checkboolean());
        return bio;
    }
}
