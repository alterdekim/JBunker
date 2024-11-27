package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Disaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DisasterRepository extends JpaRepository<Disaster, Long> {
    Optional<Disaster> findById(Long id);

    List<Disaster> findAll();

    @Query("SELECT d FROM Disaster d WHERE d.theme = :th")
    List<Disaster> findByTheme(@Param(value = "th") Long theme);

    @Transactional
    @Modifying
    @Query("UPDATE Disaster d SET d.nameTextId = :nameTextId, d.descTextId = :descTextId, d.theme = :theme WHERE d.id = :uuid")
    void updateDisaster(@Param("uuid") Long id, Long nameTextId, Long descTextId, Long theme);
}
