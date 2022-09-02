package co.wordly.service;

import co.wordly.data.dto.api.EmailDto;

public interface EmailService {

    EmailDto save(EmailDto emailDto);

    EmailDto getEmail(String email);

}
