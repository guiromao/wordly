package co.wordly.service;

import co.wordly.data.converter.PlatformJobConverter;
import co.wordly.data.entity.JobEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
@PropertySource("credentials.properties")
public class EmailSenderImpl implements EmailSender {

    private static final Logger LOG = LoggerFactory.getLogger(EmailSenderImpl.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

    private final JavaMailSender mailSender;
    private final String wordlyAccount;

    public EmailSenderImpl(JavaMailSender mailSender,
                           @Value("${spring.mail.username}") String wordlyAccount) {
        this.mailSender = mailSender;
        this.wordlyAccount = wordlyAccount;
    }

    @Override
    public void send(String toEmail, Set<JobEntity> jobs) {
        if (!CollectionUtils.isEmpty(jobs)) {
            LOG.info("Going to send an email with {} jobs to: {}", jobs.size(), toEmail);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            String formattedTime = DATE_FORMATTER.format(LocalDate.now());
            String title = "Today's Jobs!!! <"  + formattedTime + ">";
            String message = PlatformJobConverter.convertToEmailList(jobs);

            try {
                mimeMessage.setContent(message, "text/html");
                messageHelper.setFrom(wordlyAccount);
                messageHelper.setTo(toEmail);
                messageHelper.setSubject(title);

                mailSender.send(mimeMessage);
                LOG.info("Email successfully sent to: {}", toEmail);

            } catch (MailException | MessagingException e) {
                LOG.warn("Could not send email message to: {}\n" +
                        "For reason {}", toEmail, e);
            }
        } else {
            LOG.warn("No jobs match the defined criteria for user {}, today", toEmail);
        }
    }

}
