package co.wordly.data.converter;

import co.wordly.data.dto.RemotiveDto;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JobConverter {

    private final Map<String, String> sourcesMap;
    private final SourceRepository sourceRepository;

    @Autowired
    public JobConverter(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
        this.sourcesMap = fetchSourcesMap();
    }

    public JobEntity convertFromRemotive(RemotiveDto remotiveDto) {
        return new JobEntity.Builder()
                .title(remotiveDto.getJobTitle())
                .description(remotiveDto.getJobDescription())
                .sourceId(sourcesMap.get("remotive"))
                .sourceJobId(remotiveDto.getJobId())
                .companyId(null)
                .companyLogoUrl(remotiveDto.getCompanyLogoUrl())
                .publishDate(Instant.parse(remotiveDto.getPublicationDate()))
                .salary(remotiveDto.getSalary())
                .build();
    }

    private Map<String, String> fetchSourcesMap() {
        return sourceRepository.findAll()
                .stream()
                .collect(Collectors.toMap(SourceEntity::getName, SourceEntity::getId));
    }

}
