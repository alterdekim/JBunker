package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Luggage;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.repository.GameThemeRepository;
import com.alterdekim.javabot.repository.LuggageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LuggageServiceImpl implements LuggageService {

    @Autowired
    private GameThemeRepository themeRepository;

    private final LuggageRepository repository;

    @Override
    public List<Luggage> getAllLuggages() {
        return themeRepository.findAllSelected()
                .stream()
                .map(t -> repository.findByTheme(t.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Luggage getLuggageById(long luggageId) {
        return repository.findById(luggageId).orElse(null);
    }

    @Override
    public void removeById(long luggageId) {
        repository.deleteById(luggageId);
    }

    @Override
    public void saveLuggage(Luggage luggage) {
        repository.save(luggage);
    }

    @Override
    public List<Synergy> getSynergies(Long id) {
        return repository.getSynergies(id);
    }

    @Override
    public void updateLuggage(Long id, Float violence, Float power, Float asocial, Float foodstuffs, Boolean garbage, Long textNameId, Long textDescId, Long theme) {
        repository.updateLuggage(id, violence, power, asocial, foodstuffs, garbage, textNameId, textDescId, theme);
    }
}
