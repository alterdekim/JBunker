package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Disaster;
import com.alterdekim.javabot.repository.DisasterRepository;
import com.alterdekim.javabot.repository.GameThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisasterServiceImpl implements DisasterService {

    @Autowired
    private GameThemeRepository themeRepository;
    private final DisasterRepository repository;

    public DisasterServiceImpl(DisasterRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Disaster> getAllDisasters() {
        return themeRepository.findAllSelected()
                .stream()
                .map(t -> repository.findByTheme(t.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Disaster getDisasterById(long dId) {
        return repository.findById(dId).orElse(null);
    }

    @Override
    public void removeById(long dId) {
        repository.deleteById(dId);
    }

    @Override
    public void saveDisaster(Disaster disaster) {
        repository.save(disaster);
    }
}
