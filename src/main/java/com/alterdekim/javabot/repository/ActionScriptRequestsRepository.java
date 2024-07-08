package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.ActionScript;
import com.alterdekim.javabot.entities.ActionScriptRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActionScriptRequestsRepository extends JpaRepository<ActionScriptRequest, Long> {
    Optional<ActionScriptRequest> findById(Long id);

    List<ActionScriptRequest> findAll();
}
