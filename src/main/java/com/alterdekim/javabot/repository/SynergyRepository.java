package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Synergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SynergyRepository extends JpaRepository<Synergy, Long> {
    Optional<Synergy> findById(Long id);

    List<Synergy> findAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Synergy s WHERE (s.firstEntityId = :uuid AND s.firstType = :sectionType) OR (s.secondEntityId = :uuid AND s.secondType = :sectionType)")
    void deleteByEntityId(@Param("uuid") Long entityId, @Param("sectionType") Integer sectionType);
}
