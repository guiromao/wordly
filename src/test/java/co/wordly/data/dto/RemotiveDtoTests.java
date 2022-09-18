package co.wordly.data.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RemotiveDtoTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(RemotiveDto.class).verify();
    }

}
