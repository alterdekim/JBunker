package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.ActionScript;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActionScriptsRepository extends JpaRepository<ActionScript, Long> {
    Optional<ActionScript> findByScriptId(Long scriptId);

    List<ActionScript> findAll();
}
