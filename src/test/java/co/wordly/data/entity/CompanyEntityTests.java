package co.wordly.data.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class CompanyEntityTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(CompanyEntity.class)
                .withNonnullFields("id")
                .verify();
    }

}
