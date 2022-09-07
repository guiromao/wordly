package co.wordly.service;

import co.wordly.ResourceNotFoundException;
import co.wordly.data.converter.EmailConverter;
import co.wordly.data.dto.api.EmailDto;
import co.wordly.data.entity.EmailEntity;
import co.wordly.data.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    @Autowired
    public EmailServiceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public EmailDto save(EmailDto emailDto) {
        EmailEntity receivedEmail = EmailConverter.toEntity(emailDto);

        return EmailConverter.toDto(emailRepository.save(receivedEmail));
    }

    @Override
    public EmailDto getEmail(String email) {
        EmailEntity emailEntity = emailRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException(EmailEntity.class, email));

        return EmailConverter.toDto(emailEntity);
    }

}
