package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.entities.Work;
import com.alterdekim.javabot.repository.GameThemeRepository;
import com.alterdekim.javabot.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private GameThemeRepository themeRepository;

    private final WorkRepository repository;

    public WorkServiceImpl(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Work> getAllWorks() {
        return themeRepository.findAllSelected()
                .stream()
                .map(t -> repository.findByTheme(t.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Work getWorkById(long workId) {
        return repository.findById(workId).orElse(null);
    }

    @Override
    public void removeById(long workId) {
        repository.deleteById(workId);
    }

    @Override
    public void saveWork(Work work) {
        repository.save(work);
    }

    @Override
    public List<Synergy> getSynergies(Long id) {
        return repository.getSynergies(id);
    }

    @Override
    public void updateWork(Long id, Float asocial, Float power, Float violence, Float foodstuffs, Long textNameId, Long textDescId, Long theme) {
        repository.updateWork(id, asocial, power, violence, foodstuffs, textNameId, textDescId, theme);
    }


}
