package co.wordly.data.dto.apiresponse;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class LandingJobsResponseDtoTests {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(LandingJobsResponseDto.class).verify();
    }

}
