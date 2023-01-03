package co.wordly.scheduler;

import co.wordly.data.entity.EmailEntity;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.repository.EmailRepository;
import co.wordly.data.repository.JobRepository;
import co.wordly.service.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmailSendSchedulerTest {

    EmailSender emailSender;
    EmailRepository emailRepository;
    JobRepository jobRepository;

    EmailSendScheduler sendScheduler;

    List<JobEntity> jobs = List.of(
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
                    .title("Kotlin Developer")
                    .description("Everyday I'm shuff... testing! And working with Spring, too :)")
                    .publishDate(LocalDateTime.now().minus(1, ChronoUnit.DAYS)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .sourceJobId("11111")
                    .sourceId("source-3")
                    .companyId("company-3")
                    .companyLogoUrl("https://company-3.com/logo.png")
                    .salary("55000€")
                    .build()
    );

    List<EmailEntity> emailEntities = List.of(
            new EmailEntity("gromao@mail.com", Set.of("Java", "Kotlin", "Spring")),
            new EmailEntity("otheremail@mail.com", Set.of("Software")),
            new EmailEntity("tester@mail.com", Set.of("QA"))
    );

    @BeforeEach
    void setup() {
        emailSender = mock(EmailSender.class);
        emailRepository = mock(EmailRepository.class);
        jobRepository = mock(JobRepository.class);

        sendScheduler = new EmailSendScheduler(emailSender, emailRepository, jobRepository);
    }

    @Test
    void testSendEmails() {
        when(jobRepository.fetchTodayJobs()).thenReturn(jobs);
        when(emailRepository.findByOffsetAndLimit(anyInt(), anyInt()))
                .thenReturn(emailEntities)
                .thenReturn(Collections.emptyList());

        int expectedEmailsSent = (int) emailEntities.stream()
                .filter(email -> jobs.stream()
                        .anyMatch(job -> email.getKeywords().stream()
                                .anyMatch(keyword -> job.getDescription().contains(keyword) ||
                                        job.getTitle().contains(keyword))))
                .count();

        sendScheduler.sendEmails();

        verify(emailSender, times(expectedEmailsSent)).send(anyString(), anySet());
    }

}
