package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Disaster;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {
    Optional<Work> findById(Long id);

    List<Work> findAll();

    @Query(value = "SELECT new Synergy(s.id, s.firstEntityId, s.firstType, s.secondEntityId, s.secondType, s.probabilityValue) FROM Synergy s WHERE (s.firstType = 4 AND s.firstEntityId = :uuid) OR (s.secondType = 4 AND s.secondEntityId = :uuid)")
    List<Synergy> getSynergies(@Param(value = "uuid") Long workId);

    @Query("SELECT w FROM Work w WHERE w.theme = :th")
    List<Work> findByTheme(@Param(value = "th") Long theme);

    @Modifying
    @Transactional
    @Query("UPDATE Work w SET w.asocial = :asocial, w.power = :power, w.violence = :violence, w.foodstuffs = :foodstuffs, w.textNameId = :textNameId, w.textDescId = :textDescId, w.theme = :theme WHERE w.id = :uuid")
    void updateWork(@Param("uuid") Long id, @Param("asocial") Float asocial, @Param("power") Float power, @Param("violence") Float violence, @Param("foodstuffs") Float foodstuffs, @Param("textNameId") Long textNameId, @Param("textDescId") Long textDescId, @Param("theme") Long theme);
}
