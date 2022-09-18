package co.wordly.data.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class HymalaiasAppDtoTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(HymalaiasAppDto.class).verify();
    }

}
