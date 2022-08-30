package co.wordly.data.converter;

import co.wordly.configuration.JobsConfigurations;
import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.entity.CompanyEntity;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.CompanyRepository;
import co.wordly.data.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RemotiveJobConverter extends JobConverter {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Autowired
    public RemotiveJobConverter(SourceRepository sourceRepository,
                                CompanyRepository companyRepository) {
        super(sourceRepository, companyRepository);
    }

    @Override
    public Set<JobEntity> convert(ApiResponse apiResponse) {
        Set<JobDto> jobDtos = apiResponse.getJobs();

        return jobDtos.stream()
                .map(this::createEntity)
                .collect(Collectors.toSet());
    }

    private JobEntity createEntity(JobDto jobDto) {
        String sourceId = sourceRepository.findByName(JobsConfigurations.REMOTIVE)
                .map(SourceEntity::getId)
                .orElse(null);

        String companyId = companyRepository.findByName(jobDto.getCompanyName())
                .map(CompanyEntity::getId)
                .orElse(null);

        return new JobEntity.Builder()
                .title(jobDto.getTitle())
                .description(jobDto.getDescription())
                .sourceId(sourceId)
                .sourceJobId(jobDto.getSourceJobId())
                .companyId(companyId)
                .url(jobDto.getUrl())
                .companyLogoUrl(jobDto.getCompanyLogoUrl())
                .publishDate(LocalDateTime.parse(jobDto.getPublishDate(), dateFormatter))
                .salary(jobDto.getSalary())
                .build();
    }



}
