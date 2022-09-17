package co.wordly.data.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class JobEntityTests {

    @Test
    void testObject() {
        EqualsVerifier.simple().forClass(JobEntity.class)
                .withNonnullFields("id", "sourceId", "sourceJobId")
                .verify();
    }

}
