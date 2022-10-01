package co.wordly.data.converter;

import co.wordly.data.dto.JobDto;
import co.wordly.data.entity.CompanyEntity;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.CompanyRepository;
import co.wordly.data.repository.SourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RemotiveJobConverterTests {

    static final LocalDateTime TIME = LocalDateTime.now();

    SourceRepository sourceRepository;

    CompanyRepository companyRepository;

    RemotiveJobConverter jobConverter;

    @BeforeEach
    void setup() {
        sourceRepository = mock(SourceRepository.class);
        companyRepository = mock(CompanyRepository.class);

        jobConverter = new RemotiveJobConverter(sourceRepository, companyRepository);
    }

    @Test
    void testConvertJobDtos() {
        Set<JobDto> dtos = jobDtos();

        when(sourceRepository.findByName(anyString())).thenReturn(Optional.of(sourceEntity()));
        when(companyRepository.findByNames(any(Set.class))).thenReturn(companies());

        Set<JobEntity> test = jobConverter.convert(dtos);

        Assertions.assertTrue(compareEntities(expectedEntities(), test));
    }

    private boolean compareEntities(Set<JobEntity> expected, Set<JobEntity> result) {
        return expected.stream()
                .allMatch(entity -> result.stream()
                        .anyMatch(res ->
                                entity.getPublishDate().equals(res.getPublishDate()) &&
                                entity.getCompanyId().equals(res.getCompanyId()) &&
                                entity.getUrl().equals(res.getUrl()) &&
                                entity.getTitle().equals(res.getTitle()) &&
                                entity.getDescription().equals(res.getDescription()) &&
                                entity.getSalary().equals(res.getSalary()) &&
                                entity.getCompanyLogoUrl().equals(res.getCompanyLogoUrl()) &&
                                entity.getSourceJobId().equals(res.getSourceJobId()) &&
                                entity.getSourceId().equals(res.getSourceId())));
    }

    private Set<JobEntity> expectedEntities() {
        return Set.of(
                new JobEntity.Builder()
                        .title("Software Engineer Kotlin")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME)
                        .sourceId("remotive")
                        .sourceJobId("remotive-job-id-1")
                        .companyId("id-1")
                        .companyLogoUrl("http://greentech.com/1.png")
                        .url("https://job.com/software-engineer")
                        .salary("$60000")
                        .build(),
                new JobEntity.Builder()
                        .title("Software Engineer Java")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME)
                        .sourceId("remotive")
                        .sourceJobId("remotive-job-id-2")
                        .companyId("id-2")
                        .companyLogoUrl("http://healthtech.com/1.png")
                        .url("https://job.com/software-engineer-java")
                        .salary("$65000")
                        .build()
        );
    }

    private Set<JobDto> jobDtos() {
        return Set.of(
                new JobDto.Builder()
                        .title("Software Engineer Kotlin")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME.toString())
                        .sourceId("remotive")
                        .sourceJobId("remotive-job-id-1")
                        .companyName("GreenTech")
                        .companyLogoUrl("http://greentech.com/1.png")
                        .companyUrl("http://greentech.com")
                        .url("https://job.com/software-engineer")
                        .salary("$60000")
                        .build(),
                new JobDto.Builder()
                        .title("Software Engineer Java")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME.toString())
                        .sourceId("remotive")
                        .sourceJobId("remotive-job-id-2")
                        .companyName("HealthTech")
                        .companyLogoUrl("http://healthtech.com/1.png")
                        .companyUrl("http://healthtech.com")
                        .url("https://job.com/software-engineer-java")
                        .salary("$65000")
                        .build()
        );
    }

    private SourceEntity sourceEntity() {
        return new SourceEntity("remotive", "remotive");
    }

    private List<CompanyEntity> companies() {
        return List.of(
                new CompanyEntity("id-1", "GreenTech", Collections.emptyMap()),
                new CompanyEntity("id-2", "HealthTech", Collections.emptyMap())
        );
    }

}
