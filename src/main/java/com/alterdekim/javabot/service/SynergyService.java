package com.alterdekim.javabot.service;

import com.alterdekim.javabot.bot.SectionType;
import com.alterdekim.javabot.entities.Luggage;
import com.alterdekim.javabot.entities.Synergy;

import java.util.List;

public interface SynergyService {
    void removeById(long synergyId);

    void saveSynergy(Synergy synergy);

    List<Synergy> getAllSynergies();

    void removeByEntityId(Long entityId, SectionType sectionType);
}
