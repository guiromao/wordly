package co.wordly.util;

import co.wordly.data.dto.JobDto;
import co.wordly.data.entity.JobEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class TestUtils {

    public static Set<JobEntity> jobEntities() {
        return Set.of(
                new JobEntity.Builder()
                        .title("Java Software Developer")
                        .description("A Java Job you'll love!")
                        .publishDate(LocalDateTime.now().minus(2, ChronoUnit.DAYS)
                                .truncatedTo(ChronoUnit.SECONDS))
                        .sourceJobId("123456")
                        .sourceId("source-1")
                        .companyId("company-1")
                        .companyLogoUrl("https://company-1.com/logo.png")
                        .salary("65000€")
                        .build(),
                new JobEntity.Builder()
                        .title("Systems Architecht")
                        .description("Where houses and software meet!")
                        .publishDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                        .sourceJobId("15678")
                        .sourceId("source-2")
                        .companyId("company-2")
                        .companyLogoUrl("https://company-2.com/logo.png")
                        .salary("85000€")
                        .build(),
                new JobEntity.Builder()
                        .title("QA Tester")
                        .description("Everyday I'm shuff... testing!")
                        .publishDate(LocalDateTime.now().minus(1, ChronoUnit.DAYS)
                                .truncatedTo(ChronoUnit.SECONDS))
                        .sourceJobId("11111")
                        .sourceId("source-3")
                        .companyId("company-3")
                        .companyLogoUrl("https://company-3.com/logo.png")
                        .salary("55000€")
                        .build()
        );
    }

    public static Set<JobDto> jobDtos() {
        return Set.of(
                new JobDto.Builder()
                        .title("Java Software Developer")
                        .description("A Java Job you'll love!")
                        .publishDate(LocalDateTime.now().minus(2, ChronoUnit.DAYS)
                                .truncatedTo(ChronoUnit.SECONDS).toString())
                        .sourceJobId("123456")
                        .sourceId("source-1")
                        .companyId("company-1")
                        .companyLogoUrl("https://company-1.com/logo.png")
                        .salary("65000€")
                        .build(),
                new JobDto.Builder()
                        .title("Systems Architecht")
                        .description("Where houses and software meet!")
                        .publishDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString())
                        .sourceJobId("15678")
                        .sourceId("source-2")
                        .companyId("company-2")
                        .companyLogoUrl("https://company-2.com/logo.png")
                        .salary("85000€")
                        .build(),
                new JobDto.Builder()
                        .title("QA Tester")
                        .description("Everyday I'm shuff... testing!")
                        .publishDate(LocalDateTime.now().minus(1, ChronoUnit.DAYS)
                                .truncatedTo(ChronoUnit.SECONDS).toString())
                        .sourceJobId("11111")
                        .sourceId("source-3")
                        .companyId("company-3")
                        .companyLogoUrl("https://company-3.com/logo.png")
                        .salary("55000€")
                        .build()
        );
    }

}
