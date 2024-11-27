package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.GameTheme;
import com.alterdekim.javabot.repository.GameThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameThemeServiceImpl implements GameThemeService {
    private final GameThemeRepository repository;


    @Override
    public List<GameTheme> getAllGameThemes() {
        return repository.findAll();
    }

    @Override
    public GameTheme getThemeById(long themeId) {
        return repository.findById(themeId).orElse(null);
    }

    @Override
    public void removeById(long themeId) {
        repository.deleteById(themeId);
    }

    @Override
    public void saveGameTheme(GameTheme gameTheme) {
        repository.save(gameTheme);
    }

    @Override
    public List<GameTheme> getSelectedThemes() {
        return repository.findAllSelected();
    }

    @Override
    public void setThemeState(Long themeId, Boolean themeState) {
        repository.updateThemeState(themeId, themeState);
    }
}
