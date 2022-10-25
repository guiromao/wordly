package co.wordly.controller;

import co.wordly.data.dto.api.EmailDto;
import co.wordly.data.entity.EmailEntity;
import co.wordly.data.repository.EmailRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test"})
class EmailControllerTest {

    @Autowired
    EmailRepository emailRepository;

    @LocalServerPort
    int port;

    String path;

    @BeforeEach
    void setup() {
        teardown();
        emailRepository.save(new EmailEntity("existing.email@mail.com", Set.of("Java", "Spring")));

        path = "http://localhost:" + port + "/api/v1/email";
    }

    @AfterEach
    void teardown() {
        emailRepository.deleteAll();
    }

    @Test
    void testCreateEmail() {
        with().body(new EmailDto("new.email@mail.com", Set.of("Kotlin", "MongoDB")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("email", equalTo("new.email@mail.com"))
                .body("keywords", hasItems("Kotlin", "MongoDB"));
    }

    @Test
    void testGetEmail() {
        get(path + "/existing.email@mail.com")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("email", equalTo("existing.email@mail.com"))
                .body("keywords", hasItems("Java", "Spring"));
    }

}
