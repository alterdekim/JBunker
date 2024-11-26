package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.GameTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameThemeRepository extends JpaRepository<GameTheme, Long> {
    Optional<GameTheme> findById(Long id);

    List<GameTheme> findAll();
}
