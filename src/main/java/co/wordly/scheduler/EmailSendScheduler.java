package co.wordly.scheduler;

import co.wordly.data.entity.EmailEntity;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.repository.EmailRepository;
import co.wordly.data.repository.JobRepository;
import co.wordly.service.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
    Class that will send newly added Jobs, once a day, to the users that
    subscribed to the newsletter.
 */
@Profile("!test")
@Component
public class EmailSendScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(EmailSendScheduler.class);

    private final EmailSender emailSender;
    private final EmailRepository emailRepository;
    private final JobRepository jobRepository;

    @Autowired
    public EmailSendScheduler(EmailSender emailSender,
                              EmailRepository emailRepository,
                              JobRepository jobRepository) {
        this.emailSender = emailSender;
        this.emailRepository = emailRepository;
        this.jobRepository = jobRepository;
    }

    // Sending newsletter once per day | once per day once the app runs, OR, once per day at 19h00 UTC
    //@Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    @Scheduled(cron = "0 0 19 * * ?", zone = "Europe/Lisbon")
    public void sendEmails() {
        emailSendingTask();
    }

    private void emailSendingTask() {
        LOG.info("Starting to send emails for today's jobs...");

        final List<JobEntity> todaysJobs = jobRepository.fetchTodayJobs();
        final List<EmailEntity> emailAccounts = emailRepository.findAll();

        LOG.info("There are {} new jobs today", todaysJobs.size());

        emailAccounts.forEach(account -> processJobsForUser(todaysJobs, account));

        LOG.info("Send today jobs task done.");
    }

    private void processJobsForUser(final List<JobEntity> jobs, final EmailEntity account) {
        final Set<JobEntity> eligibleJobs = jobs.stream()
                .filter(job -> doesJobContainKeywords(job, account.getKeywords()))
                .collect(Collectors.toSet());

        if (!CollectionUtils.isEmpty(eligibleJobs)) {
            emailSender.send(account.getEmail(), eligibleJobs);
            LOG.info("Sent today's jobs to user {}", account.getEmail());
        }
    }

    private boolean doesJobContainKeywords(JobEntity job, Set<String> keywords) {
        String text = String.join(" ", job.getTitle().toLowerCase(),
                job.getDescription().toLowerCase());

        return keywords.stream()
                .map(String::toLowerCase)
                .anyMatch(text::contains);
    }

}
