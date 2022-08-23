package co.wordly.data.repository;

import co.wordly.data.entity.SourceEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SourceRepositoryTests {

    @Autowired
    SourceRepository sourceRepository;

    List<SourceEntity> sources = List.of(
            new SourceEntity("1", "Remotive"),
            new SourceEntity("2", "WeWorkRemotely"),
            new SourceEntity("3", "LinkedIn")
    );

    @BeforeEach
    void setup() {
        teardown();
        sourceRepository.saveAll(sources);
    }

    @AfterEach
    void teardown() {
        sourceRepository.deleteAll();
    }

    @Test
    void testFindByNames() {
        Set<String> sourceNames = Set.of("LinkedIn", "Remotive");
        List<SourceEntity> sources = sourceRepository.findByNames(sourceNames);

        int expectedSize = (int) sources.stream()
                .map(SourceEntity::getName)
                .filter(sourceNames::contains)
                .count();

        assertEquals(expectedSize, sources.size());
    }
}
