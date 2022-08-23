package co.wordly.service;

import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;

    @Autowired
    public SourceServiceImpl(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    // Save Sources not already saved in DB
    @Override
    public void handle(Set<String> sourceNames) {
        final List<SourceEntity> existingSources = sourceRepository.findByNames(sourceNames);
        final Set<String> nonExistingSources = getNonExisting(existingSources, sourceNames);

        final Set<SourceEntity> newSources = nonExistingSources.stream()
                .map(SourceEntity::create)
                .collect(Collectors.toSet());

        if (!CollectionUtils.isEmpty(newSources)) {
            sourceRepository.saveAll(newSources);
        }
    }

    private Set<String> getNonExisting(List<SourceEntity> existingSources, Set<String> sourceNames) {
        Set<String> existingSet = existingSources.stream()
                .map(SourceEntity::getName)
                .collect(Collectors.toSet());

        return sourceNames.stream()
                .filter(name -> !existingSet.contains(name))
                .collect(Collectors.toSet());
    }

}
