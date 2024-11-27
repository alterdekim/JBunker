package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Bio;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.repository.BioRepository;
import com.alterdekim.javabot.repository.GameThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BioServiceImpl implements BioService {

    @Autowired
    private GameThemeRepository themeRepository;

    private BioRepository repository;

    public BioServiceImpl(BioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Bio> getAllBios() {
        return themeRepository.findAllSelected()
                .stream()
                .map(t -> repository.findByTheme(t.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Bio getBioById(long bioId) {
        return repository.findById(bioId).orElse(null);
    }

    @Override
    public void saveBio(Bio bio) {
        repository.save(bio);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Synergy> getSynergies(Long id) {
        return repository.getSynergies(id);
    }

    @Override
    public void updateBio(Long id, Boolean isMale, Boolean isFemale, Boolean canDie, Long nameId, Long themeId) {
        repository.updateBio(id, isMale, isFemale, canDie, nameId, themeId);
    }
}
