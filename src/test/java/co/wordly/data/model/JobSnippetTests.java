package co.wordly.data.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class JobSnippetTests {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(JobSnippet.class)
                .withNonnullFields("jobId")
                .verify();
    }

}
