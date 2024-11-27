package com.alterdekim.javabot.entities;

import com.alterdekim.javabot.bot.SectionType;
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
@Table(name = "synergy")
public class Synergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long firstEntityId;

    @Enumerated(EnumType.ORDINAL)
    private SectionType firstType;

    @Column(nullable = false)
    private Long secondEntityId;

    @Enumerated(EnumType.ORDINAL)
    private SectionType secondType;

    @Column(nullable = false)
    private Float probabilityValue;

    @Column(nullable = false)
    private Long theme = 1L;

    public Synergy(Long firstEntityId, SectionType firstType, Long secondEntityId, SectionType secondType, Float probabilityValue) {
        this.firstEntityId = firstEntityId;
        this.firstType = firstType;
        this.secondEntityId = secondEntityId;
        this.secondType = secondType;
        this.probabilityValue = probabilityValue;
    }
}
