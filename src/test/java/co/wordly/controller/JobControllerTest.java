package co.wordly.controller;

import co.wordly.data.entity.JobEntity;
import co.wordly.data.repository.JobRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@ActiveProfiles(value = { "test" })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JobControllerTest {

    static final String BASIC_OFFSET_LIMIT = "&offset=0&limit=10";

    @Autowired
    JobRepository jobRepository;

    @LocalServerPort
    int port;

    String basePath;

    @BeforeEach
    void setup() {
        teardown();
        jobRepository.saveAll(jobs());

        basePath = "http://localhost:" + port + "/api/v1/jobs";
    }

    @AfterEach
    void teardown() {
        jobRepository.deleteAll();
    }

    @Test
    void testGetByKeywords() {
        final String path = basePath + "?keywords=Java,Spring,Kotlin" + BASIC_OFFSET_LIMIT;

        given()
                .with()
                .contentType(ContentType.JSON)
                .get(path)
                .then()
                .body("", hasSize(3));
    }

    private Set<JobEntity> jobs() {
        return Set.of(
                createJob("Java Developer", "A great opportunity fork working with Java and Spring",
                        LocalDateTime.of(2022, 9, 12, 0, 0)),
                createJob("Kotlin Engineer", "Kotlin for ambitious engineers",
                        LocalDateTime.of(2022, 10, 10, 0, 0)),
                createJob("Spring and AWS", "Join a great team of developers",
                        LocalDateTime.of(2022, 9, 15, 0, 0)),
                createJob("QA Tester", "Automation for those who dare",
                        LocalDateTime.of(2022, 9, 13, 0, 0)),
                createJob("C++ Developer", "Embedded Systems engineer",
                        LocalDateTime.of(2022, 10, 21, 0, 0))
        );
    }

    private JobEntity createJob(String title, String description, LocalDateTime publishDate) {
        return new JobEntity.Builder()
                .title(title)
                .description(description)
                .publishDate(publishDate)
                .build();
    }

}
