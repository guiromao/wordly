package co.wordly.data.dto.apiresponse.company;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ApiCompanyResponseLandingJobsTests {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(ApiCompanyResponseLandingJobs.class).verify();
    }

}
