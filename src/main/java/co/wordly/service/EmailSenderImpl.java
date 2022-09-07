package co.wordly.service;

import co.wordly.data.converter.PlatformJobConverter;
import co.wordly.data.entity.JobEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Set;

@Service
@PropertySource("credentials.properties")
public class EmailSenderImpl implements EmailSender {

    private static Logger LOG = LoggerFactory.getLogger(EmailSenderImpl.class);

    private final JavaMailSender mailSender;
    private String wordlyAccount;

    public EmailSenderImpl(JavaMailSender mailSender,
                           @Value("${spring.mail.username}") String wordlyAccount) {
        this.mailSender = mailSender;
        this.wordlyAccount = wordlyAccount;
    }

    @Override
    public void send(String toEmail, Set<JobEntity> jobs) {
        if (!CollectionUtils.isEmpty(jobs)) {
            LOG.info("Going to send an email with {} jobs to: {}", jobs.size(), toEmail);

            String title = "Today's Jobs!!! <"  + LocalDate.now() + ">";
            String message = PlatformJobConverter.convertToEmailList(jobs);

            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom(wordlyAccount);
            email.setTo(toEmail);
            email.setSubject(title);
            email.setText(message);

            try {
                mailSender.send(email);
                LOG.info("Email successfully sent to: {}", toEmail);

            } catch (MailException e) {
                LOG.warn("Could not send email message to: {}\n" +
                        "For reason {}", toEmail, e);
            }
        } else {
            LOG.warn("No jobs match the defined criteria for user {}, today", toEmail);
        }
    }

}
