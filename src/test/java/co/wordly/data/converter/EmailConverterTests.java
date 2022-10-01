package co.wordly.data.converter;

import co.wordly.data.converter.EmailConverter;
import co.wordly.data.dto.api.EmailDto;
import co.wordly.data.entity.EmailEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class EmailConverterTests {

    static final String EMAIL = "email@mail.com";
    static final Set<String> KEYWORDS = Set.of("java", "kotlin", "software");

    @Test
    void testConvertoToEntity() {
        EmailEntity test = EmailConverter.toEntity(emailDto());

        Assertions.assertEquals(emailEntity(), test);
    }

    @Test
    void testConvertToDto() {
        EmailDto test = EmailConverter.toDto(emailEntity());

        Assertions.assertEquals(emailDto(), test);
    }

    private EmailEntity emailEntity() {
        return new EmailEntity(EMAIL, KEYWORDS);
    }

    private EmailDto emailDto() {
        return new EmailDto(EMAIL, KEYWORDS);
    }

}
