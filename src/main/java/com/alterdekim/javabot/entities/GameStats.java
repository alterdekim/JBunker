package com.alterdekim.javabot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_stats")
public class GameStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private Short turnNumber;

    @Column(nullable = false)
    private Double probability;

    @Column(nullable = false)
    private Integer playersCount;

    public GameStats(Long gameId, Short turnNumber, Double probability, Integer playersCount) {
        this.gameId = gameId;
        this.turnNumber = turnNumber;
        this.probability = probability;
        this.playersCount = playersCount;
    }
}
