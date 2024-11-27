package com.alterdekim.javabot.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JoinFormula;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bio")
public class Bio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Bio(Boolean isMale, Boolean isFemale, Boolean canDie, Long genderTextId, Long theme) {
        this.isMale = isMale;
        this.isFemale = isFemale;
        this.canDie = canDie;
        this.genderTextId = genderTextId;
        this.theme = theme;
    }

    @Column(nullable = false)
    private Boolean isMale;

    @Column(nullable = false)
    private Boolean isFemale;

    @Column(nullable = false)
    private Boolean canDie;

    @Column(nullable = false)
    private Long genderTextId;

    @Column(nullable = false)
    private Long theme = 1L;
}
