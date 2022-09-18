package co.wordly.data.dto.api;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PlatformJobDtoTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(PlatformJobDto.class).verify();
    }

}
