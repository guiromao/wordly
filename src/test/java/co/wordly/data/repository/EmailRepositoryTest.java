package co.wordly.data.repository;

import co.wordly.data.entity.EmailEntity;
import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class EmailRepositoryTest {

    @Autowired
    EmailRepository repository;

    @BeforeEach
    void setup() {
        teardown();
        repository.saveAll(emails());
    }

    @AfterEach
    void teardown() {
        repository.deleteAll();
    }

    @Test
    void testFindByOffsetAndLimit() {
        List<EmailEntity> test = repository.findByOffsetAndLimit(0, 100);

        Assertions.assertTrue(test.size() >= 100);
    }

    private List<EmailEntity> emails() {
        return IntStream.rangeClosed(0, 200)
                .mapToObj(i -> new EmailEntity("email-" + i + "@mail.com", Set.of("Java")))
                .toList();
    }

}
