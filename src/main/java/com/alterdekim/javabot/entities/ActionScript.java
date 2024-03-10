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
@Table(name = "action_scripts")
public class ActionScript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long textNameId;

    @Column(nullable = false)
    private Long textDescId;

    @Column(nullable = false)
    private String scriptBody;

    public ActionScript(Long textNameId, Long textDescId, String scriptBody) {
        this.textNameId = textNameId;
        this.textDescId = textDescId;
        this.scriptBody = scriptBody;
    }
}
