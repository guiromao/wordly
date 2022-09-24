package co.wordly.data.dto.apiresponse;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RemotiveJobsDtoTests {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(RemotiveJobsDto.class).verify();
    }

}
