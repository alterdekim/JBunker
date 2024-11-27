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
@Table(name = "hobby")
public class Hobby {

    public Hobby(Float foodstuffs, Float power, Float violence, Float asocial, Long textDescId, Long theme) {
        this.foodstuffs = foodstuffs;
        this.power = power;
        this.violence = violence;
        this.asocial = asocial;
        this.textDescId = textDescId;
        this.theme = theme;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float asocial;

    @Column(nullable=false)
    private Float power;

    @Column(nullable=false)
    private Float violence;

    @Column(nullable=false)
    private Float foodstuffs;

    @Column(nullable = false)
    private Long textDescId;

    @Column(nullable = false)
    private Long theme = 1L;
    
    public Double getValue() {
        return ((this.getFoodstuffs().doubleValue() +
                this.getPower().doubleValue()) / 2.0d) - (((this.getViolence().doubleValue() + this.getAsocial().doubleValue()) / 2.0d));
    }
}
