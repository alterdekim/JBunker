package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.GameTheme;

import java.util.List;

public interface GameThemeService {
    List<GameTheme> getAllGameThemes();
    GameTheme getThemeById(long themeId);

    void removeById(long themeId);

    void saveGameTheme(GameTheme gameTheme);

    List<GameTheme> getSelectedThemes();

    void setThemeState(Long themeId, Boolean themeState);
}
