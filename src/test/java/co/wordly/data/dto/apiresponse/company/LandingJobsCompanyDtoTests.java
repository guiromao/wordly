package co.wordly.data.dto.apiresponse.company;

import co.wordly.data.dto.company.LandingJobsCompanyDto;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class LandingJobsCompanyDtoTests {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(LandingJobsCompanyDto.class)
                .withNonnullFields("id")
                .verify();
    }

}
