package com.example.tddspringboot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIT {
    @LocalServerPort
    int randomServerPort;

    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp(){
        this.testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void deletingKnownEntityShouldReturn404AfterDeletion(){
        long bookId = 1;
        String baseUrl = "http://localhost:" + randomServerPort;

        ResponseEntity<JsonNode> firstResult = this.testRestTemplate
                .getForEntity(baseUrl + "/api/books/" + bookId, JsonNode.class);

        this.testRestTemplate.delete(baseUrl + "/api/books/" + bookId);

    }

}
