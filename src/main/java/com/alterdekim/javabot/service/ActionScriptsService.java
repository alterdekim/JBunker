package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.ActionScript;
import com.alterdekim.javabot.entities.Luggage;

import java.util.List;

public interface ActionScriptsService {
    List<ActionScript> getAllActionScripts();
    ActionScript getActionScriptById(long scriptId);
    void removeById(long scriptId);
    void saveScript(ActionScript script);
}
