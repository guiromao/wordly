package co.wordly.service;

import co.wordly.data.dto.api.EmailDto;
import co.wordly.data.entity.EmailEntity;
import co.wordly.data.exception.ResourceNotFoundException;
import co.wordly.data.repository.EmailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmailServiceTest {

    EmailRepository emailRepository;

    EmailService emailService;

    @BeforeEach
    void setup() {
        emailRepository = mock(EmailRepository.class);

        emailService = new EmailServiceImpl(emailRepository);
    }

    @Test
    void testSaveEmail() {
        final String email = "mail@mail.com";
        final Set<String> keywords = Set.of("Java", "Kotlin", "Spring");
        EmailDto emailDto = new EmailDto(email, keywords);
        EmailEntity emailEntity = new EmailEntity(email, keywords);

        when(emailRepository.save(any(EmailEntity.class))).thenReturn(emailEntity);

        EmailDto test = emailService.save(emailDto);

        Assertions.assertEquals(emailDto, test);
    }

    @Test
    void testGetNonExistingEmail() {
        when(emailRepository.findById(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> emailService.getEmail("non-existing@mail.com")
        );
    }

    @Test
    void testGetExistingEmail() {
        when(emailRepository.findById(anyString()))
                .thenReturn(Optional.of(new EmailEntity("mail@mail.com", Set.of("Java"))));

        Assertions.assertDoesNotThrow(
                () -> {
                    EmailDto test = emailService.getEmail("non-existing@mail.com");

                    Assertions.assertEquals("mail@mail.com", test.getEmail());
                    Assertions.assertEquals(Set.of("Java"), test.getKeywords());
                },
                String.valueOf(ResourceNotFoundException.class)
        );
    }

}
