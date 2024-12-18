package com.alterdekim.javabot.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "health")
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float health_index;

    @Column(nullable = false)
    private Long textNameId;

    @Column(nullable = false)
    private Long textDescId;

    @Column(nullable = false)
    private Boolean isChildfree;

    @Column(nullable = false)
    private Long theme = 1L;

    public Health(Float health_index, Long textNameId, Long textDescId, Boolean isChildfree, Long theme) {
        this.health_index = health_index;
        this.textNameId = textNameId;
        this.textDescId = textDescId;
        this.isChildfree = isChildfree;
        this.theme = theme;
    }
}
