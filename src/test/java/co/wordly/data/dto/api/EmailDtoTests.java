package co.wordly.data.dto.api;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class EmailDtoTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(EmailDto.class)
                .withNonnullFields("email")
                .verify();
    }

}
