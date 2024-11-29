package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.GameStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameStatsRepository extends JpaRepository<GameStats, Long> {
    List<GameStats> findAll();

    Optional<GameStats> findById(Long id);
}
