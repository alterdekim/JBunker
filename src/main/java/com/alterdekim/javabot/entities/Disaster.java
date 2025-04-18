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
@Table(name = "disasters")
public class Disaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long nameTextId;

    @Column(nullable = false)
    private Long descTextId;

    @Column(nullable = false)
    private Long theme = 1L;

    public Disaster(Long nameTextId, Long descTextId, Long theme) {
        this.nameTextId = nameTextId;
        this.descTextId = descTextId;
        this.theme = theme;
    }
}
