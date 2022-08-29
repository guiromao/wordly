package co.wordly.data.converter;

import co.wordly.data.dto.JobDto;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RemotiveJobConverter implements JobConverter {

    private final Map<String, String> sourcesMap;
    private final SourceRepository sourceRepository;

    @Autowired
    public RemotiveJobConverter(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
        this.sourcesMap = fetchSourcesMap();
    }

    @Override
    public JobEntity convert(JobDto jobDto) {
        return new JobEntity.Builder()
                .title(jobDto.getTitle())
                .description(jobDto.getDescription())
                .sourceId(sourcesMap.get("remotive"))
                .sourceJobId(jobDto.getSourceJobId())
                .companyId(jobDto.getCompanyId())
                .companyLogoUrl(jobDto.getCompanyLogoUrl())
                .publishDate(Instant.parse(jobDto.getPublishDate()))
                .salary(jobDto.getSalary())
                .build();
    }

    private Map<String, String> fetchSourcesMap() {
        return sourceRepository.findAll()
                .stream()
                .collect(Collectors.toMap(SourceEntity::getName, SourceEntity::getId));
    }

}
