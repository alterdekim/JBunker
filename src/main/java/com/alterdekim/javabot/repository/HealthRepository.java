package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Health;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface HealthRepository extends JpaRepository<Health, Long> {
    Optional<Health> findById(Long id);

    List<Health> findAll();

    @Query(value = "SELECT new Synergy(s.id, s.firstEntityId, s.firstType, s.secondEntityId, s.secondType, s.probabilityValue) FROM Synergy s WHERE (s.firstType = 1 AND s.firstEntityId = :uuid) OR (s.secondType = 1 AND s.secondEntityId = :uuid)")
    List<Synergy> getSynergies(@Param(value = "uuid") Long healthId);

    @Query("SELECT h FROM Health h WHERE h.theme = :th")
    List<Health> findByTheme(@Param(value = "th") Long theme);


    @Modifying
    @Transactional
    @Query("UPDATE Health h SET h.health_index = :health_index, h.textNameId = :textNameId, h.textDescId = :textDescId, h.isChildfree = :isChildfree, h.theme = :theme WHERE h.id = :uuid")
    void updateHealth(@Param("uuid") Long id, @Param("health_index") Float health_index, @Param("textNameId") Long textNameId, @Param("textDescId") Long textDescId, @Param("isChildfree") Boolean isChildfree, @Param("theme") Long theme);
}
