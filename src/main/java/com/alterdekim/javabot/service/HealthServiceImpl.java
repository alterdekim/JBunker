package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Health;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.repository.GameThemeRepository;
import com.alterdekim.javabot.repository.HealthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    private GameThemeRepository themeRepository;

    private final HealthRepository repository;

    public HealthServiceImpl(HealthRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Health> getAllHealth() {
        return themeRepository.findAllSelected()
                .stream()
                .map(t -> repository.findByTheme(t.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Health getHealthById(long healthId) {
        return repository.findById(healthId).orElse(null);
    }

    @Override
    public void removeById(long healthId) {
        repository.deleteById(healthId);
    }

    @Override
    public void saveHealth(Health health) {
        repository.save(health);
    }

    @Override
    public List<Synergy> getSynergies(Long id) {
        return repository.getSynergies(id);
    }

    @Override
    public void updateHealth(Long id, Float health_index, Long textNameId, Long textDescId, Boolean isChildfree, Long theme) {
        repository.updateHealth(id, health_index, textNameId, textDescId, isChildfree, theme);
    }
}
