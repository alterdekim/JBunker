package com.alterdekim.javabot.entities;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_themes")
public class GameTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long textNameId;

    @Column(nullable = false)
    private Boolean isSelected = true;

    public GameTheme(Long textNameId) {
        this.textNameId = textNameId;
    }
}
