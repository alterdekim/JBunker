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
@Table(name = "games_list")
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean hasEnded;

    @Column(nullable = false)
    private Boolean hasStoppedManually;

    @Column(nullable = false)
    private Boolean isWon;

    @Column(nullable = false)
    private Long started;

    public Games(Boolean hasEnded, Boolean hasStoppedManually, Boolean isWon, Long started) {
        this.hasEnded = hasEnded;
        this.hasStoppedManually = hasStoppedManually;
        this.isWon = isWon;
        this.started = started;
    }
}
