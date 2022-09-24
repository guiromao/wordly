package co.wordly.data.dto.apiresponse;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class HymalaiasAppResponseDtoTests {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(HymalaiasAppResponseDto.class).verify();
    }

}
