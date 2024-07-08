package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.ActionScript;
import com.alterdekim.javabot.entities.ActionScriptRequest;
import com.alterdekim.javabot.repository.ActionScriptRequestsRepository;
import com.alterdekim.javabot.repository.ActionScriptsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActionRequestServiceImpl implements ActionRequestService {

    private final ActionScriptRequestsRepository actionScriptsRepository;

    @Override
    public List<ActionScriptRequest> getAllActionScripts() {
        return actionScriptsRepository.findAll();
    }

    @Override
    public ActionScriptRequest getActionScriptById(long scriptId) {
        return actionScriptsRepository.findById(scriptId).orElse(null);
    }

    @Override
    public void removeById(long scriptId) {
        actionScriptsRepository.deleteById(scriptId);
    }

    @Override
    public void saveScript(ActionScriptRequest script) {
        actionScriptsRepository.save(script);
    }
}

