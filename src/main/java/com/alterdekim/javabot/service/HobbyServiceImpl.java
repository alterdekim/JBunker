package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Hobby;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.repository.GameThemeRepository;
import com.alterdekim.javabot.repository.HobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HobbyServiceImpl implements HobbyService {

    @Autowired
    private GameThemeRepository themeRepository;

    private final HobbyRepository repository;

    public HobbyServiceImpl(HobbyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Hobby> getAllHobbies() {
        return themeRepository.findAllSelected()
                .stream()
                .map(t -> repository.findByTheme(t.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Hobby getHobbyById(long hobbyId) {
        return repository.findById(hobbyId).orElse(null);
    }

    @Override
    public void saveHobby(Hobby hobby) {
        repository.save(hobby);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Synergy> getSynergies(Long id) {
        return repository.getSynergies(id);
    }
}
