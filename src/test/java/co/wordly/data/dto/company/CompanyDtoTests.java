package co.wordly.data.dto.company;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class CompanyDtoTests {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(CompanyDto.class).verify();
    }

}
