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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LandingJobsJobConverterTests {

    static final LocalDateTime TIME = LocalDateTime.now();

    SourceRepository sourceRepository;

    CompanyRepository companyRepository;

    LandingJobsJobConverter jobConverter;

    @BeforeEach
    void setup() {
        sourceRepository = mock(SourceRepository.class);
        companyRepository = mock(CompanyRepository.class);

        jobConverter = new LandingJobsJobConverter(sourceRepository, companyRepository);
    }

    @Test
    void testConvertJobDtos() {
        Set<JobDto> dtos = jobDtos();

        when(sourceRepository.findByName(anyString())).thenReturn(Optional.of(sourceEntity()));
        when(companyRepository.findBySource(anyString())).thenReturn(companies());

        Set<JobEntity> test = jobConverter.convert(dtos);

        System.out.println(expectedEntities());
        System.out.println(test);
        Assertions.assertTrue(compareEntities(expectedEntities(), test));
    }

    private boolean compareEntities(Set<JobEntity> expected, Set<JobEntity> result) {
        return expected.stream()
                .allMatch(entity -> result.stream()
                        .anyMatch(res ->
                                entity.getPublishDate().truncatedTo(ChronoUnit.SECONDS)
                                        .equals(res.getPublishDate().truncatedTo(ChronoUnit.SECONDS)) &&
                                        entity.getCompanyId().equals(res.getCompanyId()) &&
                                        entity.getUrl().equals(res.getUrl()) &&
                                        entity.getTitle().equals(res.getTitle()) &&
                                        entity.getDescription().equals(res.getDescription()) &&
                                        entity.getSalary().equals(res.getSalary()) &&
                                        entity.getSourceJobId().equals(res.getSourceJobId()) &&
                                        entity.getSourceId().equals(res.getSourceId())));
    }

    private Set<JobEntity> expectedEntities() {
        return Set.of(
                new JobEntity.Builder()
                        .title("Software Engineer Kotlin")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME)
                        .sourceId("landingjobs-id")
                        .sourceJobId("landingjobs-job-id-1")
                        .companyId("id-1")
                        .url("https://job.com/software-engineer")
                        .salary("$60000")
                        .build(),
                new JobEntity.Builder()
                        .title("Software Engineer Java")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME)
                        .sourceId("landingjobs-id")
                        .sourceJobId("landingjobs-job-id-2")
                        .companyId("id-2")
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
                        .sourceId("landingjobs-id")
                        .sourceJobId("landingjobs-job-id-1")
                        .companyId("id-1")
                        .companyLogoUrl("http://greentech.com/1.png")
                        .url("https://job.com/software-engineer")
                        .salary("$60000")
                        .build(),
                new JobDto.Builder()
                        .title("Software Engineer Java")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME.toString())
                        .sourceId("landingjobs")
                        .sourceJobId("landingjobs-job-id-2")
                        .companyId("id-2")
                        .companyUrl("http://healthtech.com")
                        .url("https://job.com/software-engineer-java")
                        .salary("$65000")
                        .build()
        );
    }

    private SourceEntity sourceEntity() {
        return new SourceEntity("landingjobs-id", "landingjobs");
    }

    private List<CompanyEntity> companies() {
        return List.of(
                new CompanyEntity("id-1", "GreenTech",
                        Map.of("landingjobs-id", "id-1")),
                new CompanyEntity("id-2", "HealthTech",
                        Map.of("landingjobs-id", "id-2"))
        );
    }

}
