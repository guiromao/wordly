package co.wordly.data.converter;

import co.wordly.configuration.JobsConfigurations;
import co.wordly.data.dto.JobDto;
import co.wordly.data.entity.CompanyEntity;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.CompanyRepository;
import co.wordly.data.repository.SourceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LandingJobsJobConverter extends JobConverter {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public LandingJobsJobConverter(SourceRepository sourceRepository,
                                   CompanyRepository companyRepository) {
        super(sourceRepository, companyRepository);
    }

    @Override
    public Set<JobEntity> convert(Set<JobDto> jobDtos) {
        final String landingJobsId = sourceRepository.findByName(JobsConfigurations.LANDING_JOBS)
                .map(SourceEntity::getId)
                .orElse("");

        Set<String> companyIds = jobDtos.stream()
                .map(JobDto::getCompanyId)
                .collect(Collectors.toSet());

        Map<String, String> companyPlatformIds = companyRepository.findBySource(landingJobsId, companyIds)
                .stream()
                .filter(company -> isCompanyInSet(company, companyIds, landingJobsId))
                .collect(Collectors.toMap(company -> company.getSourceRelations().get(landingJobsId),
                        CompanyEntity::getId));

        return jobDtos.stream()
                .map(dto -> toEntity(dto, landingJobsId, companyPlatformIds))
                .collect(Collectors.toSet());
    }

    private JobEntity toEntity(JobDto dto, String sourceId, Map<String, String> companyMappings) {
        String currDate = dto.getPublishDate();
        String dateStr = currDate.substring(0, currDate.indexOf("."));

        return new JobEntity.Builder()
                .sourceId(sourceId)
                .salary(dto.getSalary())
                .companyId(companyMappings.get(dto.getCompanyId()))
                .sourceJobId(dto.getSourceJobId())
                .sourceId(sourceId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .publishDate(LocalDateTime.parse(dateStr, dateFormatter))
                .build();
    }

    private boolean isCompanyInSet(CompanyEntity company, Set<String> companyIds, String sourceId) {
        String sourceCompanyId = company.getSourceRelations().get(sourceId);

        return companyIds.contains(sourceCompanyId);
    }

}
