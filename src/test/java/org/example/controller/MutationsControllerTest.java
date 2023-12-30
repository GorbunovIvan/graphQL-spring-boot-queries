package org.example.controller;

import org.example.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MutationsControllerTest {

    private HttpGraphQlTester graphQlTest;

    @BeforeEach
    void setUp(@Autowired ApplicationContext applicationContext) {

        if (graphQlTest == null) {

            var webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                    .configureClient()
                    .baseUrl("/graphql")
                    .build();

            graphQlTest = HttpGraphQlTester.create(webTestClient);
        }
    }

    @Test
    void testAddBook() {

        for (int i = 1; i <= 5; i++) {

            String query = """
                mutation {
                  addBook(name: "book-name") {
                    id
                    name
                  }
                }
                """;

            graphQlTest.document(query)
                    .execute()
                    .path("data.addBook")
                    .hasValue()
                    .entity(Book.class)
                    .isEqualTo(new Book(i, "book-name", null));
        }
    }
}