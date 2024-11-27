package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.GameTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GameThemeRepository extends JpaRepository<GameTheme, Long> {
    Optional<GameTheme> findById(Long id);

    List<GameTheme> findAll();

    @Query("SELECT t FROM GameTheme t WHERE t.isSelected = true")
    List<GameTheme> findAllSelected();

    @Transactional
    @Modifying
    @Query("UPDATE GameTheme t SET t.isSelected = :state WHERE t.id = :uuid")
    void updateThemeState(@Param("uuid") Long themeId, @Param("state") Boolean themeState);
}
