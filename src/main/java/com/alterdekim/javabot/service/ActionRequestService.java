package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.ActionScript;
import com.alterdekim.javabot.entities.ActionScriptRequest;

import java.util.List;

public interface ActionRequestService {
    List<ActionScriptRequest> getAllActionScripts();
    ActionScriptRequest getActionScriptById(long scriptId);
    void removeById(long scriptId);
    void saveScript(ActionScriptRequest script);
}
