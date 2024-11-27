package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Bio;
import com.alterdekim.javabot.entities.Synergy;

import java.util.List;

public interface BioService {
    List<Bio> getAllBios();
    Bio getBioById(long bioId);

    void saveBio(Bio bio);

    void removeById(Long id);

    List<Synergy> getSynergies(Long id);

    void updateBio(Long id, Boolean isMale, Boolean isFemale, Boolean canDie, Long nameId, Long themeId);
}
