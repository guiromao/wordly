package co.wordly.data.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class EmailEntityTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(EmailEntity.class)
                .withNonnullFields("email")
                .verify();
    }

}
