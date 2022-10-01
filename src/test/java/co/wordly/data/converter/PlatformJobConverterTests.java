package co.wordly.data.converter;

import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.data.entity.JobEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class PlatformJobConverterTests {

    static final LocalDateTime TIME = LocalDateTime.now();

    @Test
    void testConvertToPlatformJobDtos() {
        Set<PlatformJobDto> test = PlatformJobConverter.convert(jobEntities());

        Assertions.assertTrue(compareDtosAndEntities(test, new HashSet<>(jobEntities())));
    }

    @Test
    void testConvertToEmailString() {
        final String test = PlatformJobConverter.convertToEmailList(new HashSet<>(jobEntities()));

        jobEntities()
                .forEach(entity -> {
                    Assertions.assertTrue(test.contains(entity.getTitle()));
                    Assertions.assertTrue(test.contains(entity.getDescription()));
                });
    }

    private boolean compareDtosAndEntities(Set<PlatformJobDto> result,
                                           Set<JobEntity> entities) {
        return result.stream()
                .allMatch(res -> entities.stream()
                        .anyMatch(entity -> entity.getTitle().equals(res.title()) &&
                                Objects.equals(entity.getSalary(), res.salary()) &&
                                entity.getDescription().equals(res.description()) &&
                                entity.getPublishDate().truncatedTo(ChronoUnit.SECONDS)
                                        .equals(res.publishDate().truncatedTo(ChronoUnit.SECONDS)) &&
                                entity.getUrl().equals(res.url()) &&
                                entity.getCompanyLogoUrl().equals(res.companyLogoUrl())));
    }

    private Set<PlatformJobDto> expectedPlatformJobs() {
        return Set.of(
                new PlatformJobDto.Builder()
                        .title("Software Engineer Kotlin")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME)
                        .companyLogoUrl("http://greentech.com/1.png")
                        .url("https://job.com/software-engineer")
                        .salary("$60000")
                        .build(),
                new PlatformJobDto.Builder()
                        .title("Software Engineer Java")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME)
                        .companyLogoUrl("http://healthtech.com/1.png")
                        .url("https://job.com/software-engineer-java")
                        .salary("$65000")
                        .build()
        );
    }

    private List<JobEntity> jobEntities() {
        return List.of(
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

}
