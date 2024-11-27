package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Health;
import com.alterdekim.javabot.entities.Hobby;
import com.alterdekim.javabot.entities.Synergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    Optional<Hobby> findById(Long id);

    List<Hobby> findAll();

    @Query(value = "SELECT new Synergy(s.id, s.firstEntityId, s.firstType, s.secondEntityId, s.secondType, s.probabilityValue) FROM Synergy s WHERE (s.firstType = 2 AND s.firstEntityId = :uuid) OR (s.secondType = 2 AND s.secondEntityId = :uuid)")
    List<Synergy> getSynergies(@Param(value = "uuid") Long hobbId);

    @Query("SELECT h FROM Hobby h WHERE h.theme = :th")
    List<Hobby> findByTheme(@Param(value = "th") Long theme);

    @Modifying
    @Transactional
    @Query("UPDATE Hobby h SET h.foodstuffs = :foodstuffs, h.power = :power, h.violence = :violence, h.asocial = :asocial, h.textDescId = :nameId, h.theme = :themeId WHERE h.id = :uuid")
    void updateHobby(@Param("uuid") Long id, @Param("foodstuffs") Float foodstuffs, @Param("power") Float power, @Param("violence") Float violence, @Param("asocial") Float asocial, Long nameId, Long themeId);
}
