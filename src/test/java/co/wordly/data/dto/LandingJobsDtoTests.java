package co.wordly.data.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class LandingJobsDtoTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(LandingJobsDto.class)
                .withNonnullFields("jobId")
                .verify();
    }

}
