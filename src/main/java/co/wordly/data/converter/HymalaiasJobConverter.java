package co.wordly.data.converter;

import co.wordly.configuration.JobsConfigurations;
import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.entity.CompanyEntity;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.CompanyRepository;
import co.wordly.data.repository.SourceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HymalaiasJobConverter extends JobConverter {

    public HymalaiasJobConverter(SourceRepository sourceRepository,
                                 CompanyRepository companyRepository) {
        super(sourceRepository, companyRepository);
    }

    @Override
    public Set<JobEntity> convert(Set<JobDto> jobDtos) {
        final String sourceId = sourceRepository.findByName(JobsConfigurations.HYMALAIAS_APP)
                .map(SourceEntity::getId)
                .orElse(JobsConfigurations.HYMALAIAS_APP);
        final Set<String> companyNames = jobDtos.stream()
                .map(JobDto::getCompanyName)
                .collect(Collectors.toSet());
        final List<CompanyEntity> companies = companyRepository.findByNames(companyNames);

        return jobDtos.stream()
                .map(dto -> convert(dto, sourceId, companies))
                .collect(Collectors.toSet());
    }

    private JobEntity convert(JobDto jobDto, String sourceId, List<CompanyEntity> companies) {
        final String companyId = companies.stream()
                .map(CompanyEntity::getName)
                .filter(companyName -> jobDto.getCompanyName().equals(companyName))
                .findFirst()
                .orElse(null);
        final int publishEpoch = Integer.parseInt(jobDto.getPublishDate());

        return new JobEntity.Builder()
                .title(jobDto.getTitle())
                .description(jobDto.getDescription())
                .companyId(companyId)
                .companyLogoUrl(jobDto.getCompanyLogoUrl())
                .sourceJobId(jobDto.getSourceJobId())
                .sourceId(sourceId)
                .url(jobDto.getUrl())
                .publishDate(LocalDateTime.ofEpochSecond(publishEpoch, 0, ZoneOffset.UTC))
                .build();
    }
}
