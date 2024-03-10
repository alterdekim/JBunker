package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.ActionScript;
import com.alterdekim.javabot.repository.ActionScriptsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActionScriptsServiceImpl implements ActionScriptsService {

    private final ActionScriptsRepository actionScriptsRepository;

    @Override
    public List<ActionScript> getAllActionScripts() {
        return actionScriptsRepository.findAll();
    }

    @Override
    public ActionScript getActionScriptById(long scriptId) {
        return actionScriptsRepository.findByScriptId(scriptId).orElse(null);
    }

    @Override
    public void removeById(long scriptId) {
        actionScriptsRepository.deleteById(scriptId);
    }

    @Override
    public void saveScript(ActionScript script) {
        actionScriptsRepository.save(script);
    }
}
