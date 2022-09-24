package co.wordly.data.converter;

import co.wordly.configuration.JobsConfigurations;
import co.wordly.data.dto.JobDto;
import co.wordly.data.entity.CompanyEntity;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.CompanyRepository;
import co.wordly.data.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    public Set<JobEntity> convert(Set<JobDto> jobDtos) {
        final String sourceId = sourceRepository.findByName(JobsConfigurations.REMOTIVE)
                .map(SourceEntity::getId)
                .orElse(null);
        Set<String> companyNames = jobDtos.stream()
                .map(JobDto::getCompanyName)
                .collect(Collectors.toSet());
        List<CompanyEntity> companies = companyRepository.findByNames(companyNames);

        return jobDtos.stream()
                .map(dto -> createEntity(dto, sourceId, companies))
                .collect(Collectors.toSet());
    }

    private JobEntity createEntity(JobDto jobDto, String sourceId, List<CompanyEntity> companies) {
        String companyId = companies.stream()
                .filter(company -> company.getName().equals(jobDto.getCompanyName()))
                .map(CompanyEntity::getId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Company not found"));

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
