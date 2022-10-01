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
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class HymalaiasJobConverterTests {

    static final LocalDateTime TIME = LocalDateTime.now();

    SourceRepository sourceRepository;
    CompanyRepository companyRepository;

    HymalaiasJobConverter jobConverter;

    @BeforeEach
    void setup() {
        sourceRepository = Mockito.mock(SourceRepository.class);
        companyRepository = Mockito.mock(CompanyRepository.class);

        jobConverter = new HymalaiasJobConverter(sourceRepository, companyRepository);
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
                                entity.getPublishDate().truncatedTo(ChronoUnit.SECONDS)
                                        .equals(res.getPublishDate().truncatedTo(ChronoUnit.SECONDS)) &&
                                        entity.getCompanyId().equals(res.getCompanyId()) &&
                                        entity.getUrl().equals(res.getUrl()) &&
                                        entity.getTitle().equals(res.getTitle()) &&
                                        entity.getDescription().equals(res.getDescription()) &&
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
                        .sourceId("hymalaiasapp-id")
                        .sourceJobId("hymalaiasapp-job-id-1")
                        .companyId("id-1")
                        .companyLogoUrl("http://greentech.com/1.png")
                        .url("https://job.com/software-engineer")
                        .build(),
                new JobEntity.Builder()
                        .title("Software Engineer Java")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME)
                        .sourceId("hymalaiasapp-id")
                        .sourceJobId("hymalaiasapp-job-id-2")
                        .companyId("id-2")
                        .companyLogoUrl("http://healthtech.com/1.png")
                        .url("https://job.com/software-engineer-java")
                        .build()
        );
    }

    private Set<JobDto> jobDtos() {
        return Set.of(
                new JobDto.Builder()
                        .title("Software Engineer Kotlin")
                        .description("Software Engineer for great projects")
                        .publishDate(String.valueOf(TIME.toEpochSecond(ZoneOffset.UTC)))
                        .sourceId("hymalaiasapp-id")
                        .sourceJobId("hymalaiasapp-job-id-1")
                        .companyName("GreenTech")
                        .companyLogoUrl("http://greentech.com/1.png")
                        .companyUrl("http://greentech.com")
                        .url("https://job.com/software-engineer")
                        .build(),
                new JobDto.Builder()
                        .title("Software Engineer Java")
                        .description("Software Engineer for great projects")
                        .publishDate(String.valueOf(TIME.toEpochSecond(ZoneOffset.UTC)))
                        .sourceId("hymalaiasapp-id")
                        .sourceJobId("hymalaiasapp-job-id-2")
                        .companyName("HealthTech")
                        .companyLogoUrl("http://healthtech.com/1.png")
                        .companyUrl("http://healthtech.com")
                        .url("https://job.com/software-engineer-java")
                        .build()
        );
    }

    private SourceEntity sourceEntity() {
        return new SourceEntity("hymalaiasapp-id", "hymalaiasapp");
    }

    private List<CompanyEntity> companies() {
        return List.of(
                new CompanyEntity("id-1", "GreenTech", Collections.emptyMap()),
                new CompanyEntity("id-2", "HealthTech", Collections.emptyMap())
        );
    }

}
