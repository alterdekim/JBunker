package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Hobby;
import com.alterdekim.javabot.entities.Luggage;
import com.alterdekim.javabot.entities.Synergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LuggageRepository extends JpaRepository<Luggage, Long> {
    Optional<Luggage> findById(Long id);

    List<Luggage> findAll();

    @Query(value = "SELECT new Synergy(s.id, s.firstEntityId, s.firstType, s.secondEntityId, s.secondType, s.probabilityValue) FROM Synergy s WHERE (s.firstType = 3 AND s.firstEntityId = :uuid) OR (s.secondType = 3 AND s.secondEntityId = :uuid)")
    List<Synergy> getSynergies(@Param(value = "uuid") Long luggId);

    @Query("SELECT l FROM Luggage l WHERE l.theme = :th")
    List<Luggage> findByTheme(@Param(value = "th") Long theme);

    @Modifying
    @Transactional
    @Query("UPDATE Luggage l SET l.violence = :violence, l.power = :power, l.asocial = :asocial, l.foodstuffs = :foodstuffs, l.garbage = :garbage, l.textNameId = :textNameId, l.textDescId = :textDescId, l.theme = :theme WHERE l.id = :uuid")
    void updateLuggage(@Param("uuid") Long id, @Param("violence") Float violence, @Param("power") Float power, @Param("asocial") Float asocial, @Param("foodstuffs") Float foodstuffs, @Param("garbage") Boolean garbage, @Param("textNameId") Long textNameId, @Param("textDescId") Long textDescId, @Param("theme") Long theme);
}
