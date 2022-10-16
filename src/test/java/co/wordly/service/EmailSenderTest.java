package co.wordly.service;

import co.wordly.data.entity.JobEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

class EmailSenderTest {

    JavaMailSender javaMailSender;
    String worldyAccount = "account.wordly@mail.com";

    EmailSender emailSender;

    @BeforeEach
    void setup() {
        javaMailSender = Mockito.mock(JavaMailSender.class);

        emailSender = new EmailSenderImpl(javaMailSender, worldyAccount);
    }

    @Test
    void testSendEmptyJobs() {
        emailSender.send("email@email.com", Collections.emptySet());

        Mockito.verifyNoInteractions(javaMailSender);
    }

    @Test
    void testSendEmail() {
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(Mockito.mock(MimeMessage.class));

        emailSender.send("email@email.com", jobs());

        Mockito.verify(javaMailSender, Mockito.times(1))
                .send(any(MimeMessage.class));
    }

    private Set<JobEntity> jobs() {
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



}
