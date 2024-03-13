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
@Table(name = "work")
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Float asocial;

    @Column(nullable=false)
    private Float power;

    @Column(nullable=false)
    private Float violence;

    @Column(nullable=false)
    private Float foodstuffs;

    @Column(nullable = false)
    private Long textNameId;

    @Column(nullable = false)
    private Long textDescId;

    public Work(Float asocial, Float power, Float violence, Float foodstuffs, Long textNameId, Long textDescId) {
        this.asocial = asocial;
        this.power = power;
        this.violence = violence;
        this.foodstuffs = foodstuffs;
        this.textNameId = textNameId;
        this.textDescId = textDescId;
    }
    
    public Double getValue() {
        return ((this.getFoodstuffs().doubleValue() +
                this.getPower().doubleValue()) * 0.5d) - (((this.getViolence().doubleValue() + this.getAsocial().doubleValue()) / 2.0d));
    }
}
