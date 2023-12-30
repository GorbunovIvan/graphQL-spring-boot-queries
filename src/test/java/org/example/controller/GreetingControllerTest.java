package org.example.controller;

import org.example.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingControllerTest {

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
    void testHello() {

        String query = """
                {
                    hello
                }
                """;

        graphQlTest.document(query)
                .execute()
                .path("data.hello")
                .hasValue()
                .entity(String.class)
                .isEqualTo("Hello, world");
    }

    @Test
    void testHelloWithParam() {

        String paramName = "test-name";

        String query = """
                {
                    helloWithParam(name: "%s")
                }
                """;

        query = String.format(query, paramName);

        graphQlTest.document(query)
                .execute()
                .path("data.helloWithParam")
                .hasValue()
                .entity(String.class)
                .isEqualTo("Hello, " + paramName);
    }

    @Test
    void testListOfRandomNumbers() {

        int size = 10;

        String query = """
                {
                    listOfRandomNumbers(size: %d)
                }
                """;

        query = String.format(query, size);

        graphQlTest.document(query)
                .execute()
                .path("data.listOfRandomNumbers")
                .hasValue()
                .entityList(Integer.class)
                .hasSize(size);
    }

    @Test
    void testListReactive() {

        String query = """
                {
                    listReactive
                }
                """;

        graphQlTest.document(query)
                .execute()
                .path("data.listReactive")
                .hasValue()
                .entityList(String.class)
                .isEqualTo(List.of( "a", "b", "c" ));
    }

    @Test
    void testBooks() {

        String query = """
                {
                  books {
                    id
                    name
                  }
                }
                """;

        graphQlTest.document(query)
                .execute()
                .path("data.books")
                .hasValue()
                .entityList(Book.class)
                .hasSize(3);
    }

    @Test
    void testBooksWithAuthors() {

        String query = """
                {
                  books {
                    id
                    name
                    author {
                      id
                      name
                    }
                  }
                }
                """;

        graphQlTest.document(query)
                .execute()
                .path("data.books")
                .hasValue()
                .entityList(Book.class)
                .hasSize(3);
    }
}