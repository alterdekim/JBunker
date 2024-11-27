package com.alterdekim.javabot.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.WhereJoinTable;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "luggage")
public class Luggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Luggage(Float violence, Float power, Float asocial, Float foodstuffs, Boolean garbage, Long textNameId, Long textDescId, Long theme) {
        this.violence = violence;
        this.power = power;
        this.asocial = asocial;
        this.foodstuffs = foodstuffs;
        this.garbage = garbage;
        this.textNameId = textNameId;
        this.textDescId = textDescId;
        this.theme = theme;
    }

    @Column(nullable=false)
    private Float violence;

    @Column(nullable=false)
    private Float power;

    @Column(nullable = false)
    private Float asocial;

    @Column(nullable=false)
    private Float foodstuffs;

    @Column(nullable=false)
    private Boolean garbage;

    @Column(nullable = false)
    private Long textNameId;

    @Column(nullable = false)
    private Long textDescId;

    @Column(nullable = false)
    private Long theme = 1L;
    
    public Double getValue() {
        return ((this.getFoodstuffs().doubleValue() +
                this.getPower().doubleValue()) * 0.5d) - (((this.getViolence().doubleValue() + this.getAsocial().doubleValue()) / 2.0d));
    }
}
