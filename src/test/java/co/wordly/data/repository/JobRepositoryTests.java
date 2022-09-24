package co.wordly.data.repository;

import co.wordly.data.entity.JobEntity;
import co.wordly.data.model.JobSnippet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class JobRepositoryTests {

    @Autowired
    JobRepository jobRepository;

    Set<JobEntity> jobs = Set.of(
            new JobEntity.Builder()
                    .title("Java Software Developer")
                    .description("A Jaca Job you'll love!")
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

    @BeforeEach
    void setup() {
        jobRepository.saveAll(jobs);
    }

    @AfterEach
    void teardown() {
        jobRepository.deleteAll();
    }

    @Test
    void testFindJobSnippets() {
        Set<JobSnippet> test = jobRepository.getSourceJobIdsDetails();
        Set<String> existingJobIds = jobs.stream()
                        .map(JobEntity::getId)
                        .collect(Collectors.toSet());

        Assertions.assertEquals(jobs.size(), test.size());
        Assertions.assertTrue(
                test.stream()
                        .allMatch(snippet -> existingJobIds.contains(snippet.getJobId()))
        );
    }

    @Test
    void testFetchTodayJobs() {
        List<JobEntity> expectedList = jobs.stream()
                .filter(job -> LocalDate.now().atStartOfDay().isBefore(job.getCreationDate()))
                .toList();

        List<JobEntity> test = jobRepository.fetchTodayJobs();

        Assertions.assertEquals(expectedList, test);
    }

    @Test
    void testQuery() {
        final Set<String> keywords = Set.of("Java", "QA");
        final LocalDateTime fromDate = LocalDateTime.now().minus(3, ChronoUnit.DAYS);
        final LocalDateTime toDate = LocalDateTime.now();

        final Set<JobEntity> expectedList = jobs.stream()
                .filter(job -> stringContains(job.getTitle(), keywords) ||
                        stringContains(job.getDescription(), keywords))
                .filter(job -> job.getPublishDate().isAfter(fromDate))
                .filter(job -> job.getPublishDate().isBefore(toDate))
                .collect(Collectors.toSet());

        Set<JobEntity> test = new HashSet<>(jobRepository.fetchJobs(keywords, fromDate, toDate,
                0, 50));

        Assertions.assertEquals(expectedList, test);
    }

    private boolean stringContains(String field, Set<String> keywords) {
        for (String keyword: keywords) {
            if (field.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

}
