package co.wordly.data.dto.error;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ErrorResponseTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(ErrorResponse.class).verify();
    }

}
