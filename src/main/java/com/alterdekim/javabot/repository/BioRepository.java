package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Bio;
import com.alterdekim.javabot.entities.Disaster;
import com.alterdekim.javabot.entities.Synergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BioRepository extends JpaRepository<Bio, Long> {
    Optional<Bio> findById(Long id);

    List<Bio> findAll();

    @Query(value = "SELECT new Synergy(s.id, s.firstEntityId, s.firstType, s.secondEntityId, s.secondType, s.probabilityValue) FROM Synergy s WHERE (s.firstType = 0 AND s.firstEntityId = :uuid) OR (s.secondType = 0 AND s.secondEntityId = :uuid)")
    List<Synergy> getSynergies(@Param(value = "uuid") Long bioId);

    @Query("SELECT b FROM Bio b WHERE b.theme = :th")
    List<Bio> findByTheme(@Param(value = "th") Long theme);

    @Modifying
    @Transactional
    @Query("UPDATE Bio b SET b.isMale = :isMale, b.isFemale = :isFemale, b.canDie = :canDie, b.genderTextId = :nameId, b.theme = :themeId WHERE b.id = :uuid")
    void updateBio(@Param("uuid") Long id, @Param("isMale") Boolean isMale, @Param("isFemale") Boolean isFemale, @Param("canDie") Boolean canDie, @Param("nameId") Long nameId, @Param("themeId") Long themeId);
}