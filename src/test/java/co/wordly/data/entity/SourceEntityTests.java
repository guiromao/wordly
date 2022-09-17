package co.wordly.data.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class SourceEntityTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(SourceEntity.class)
                .withNonnullFields("id", "name")
                .verify();
    }

}
