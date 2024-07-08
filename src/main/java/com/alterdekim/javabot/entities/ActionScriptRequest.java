package com.alterdekim.javabot.entities;


import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "action_scripts_req")
public class ActionScriptRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String textName;

    @Column(nullable = false)
    private String textDesc;

    @Column(nullable = false)
    private String scriptBody;

    public ActionScriptRequest(String textName, String textDesc, String scriptBody) {
        this.textName = textName;
        this.textDesc = textDesc;
        this.scriptBody = scriptBody;
    }
}
