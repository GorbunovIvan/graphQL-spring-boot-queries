package org.example.controller;

import org.example.model.Author;
import org.example.model.Book;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Run and go to <a href="http://localhost:8080/graphiql?path=/graphql">page</a>
 */
@Controller
public class GreetingController {

    /**
     * Query must be like:
     * {
     *   hello
     * }
     */
    @QueryMapping
    //@SchemaMapping(typeName = "Query", field = "hello") - just the same as "@QueryMapping"
    public String hello() {
        return "Hello, world";
    }

    /**
     * Query must be like:
     * {
     *   helloWithParam(name: ?)
     * }
     */
    @QueryMapping
    public String helloWithParam(@Argument String name) {
        return "Hello, " + name;
    }

    /**
     * Query must be like:
     * {
     * 	listOfRandomNumbers(size: 10)
     * }
     */
    @QueryMapping
    public List<Integer> listOfRandomNumbers(@Argument Integer size) {
        return new Random().ints(size)
                .boxed()
                .toList();
    }

    /**
     * Query must be like:
     * {
     * 	listReactive
     * }
     */
    @QueryMapping
    public Flux<String> listReactive() {
        return Flux.fromArray(new String[] { "a", "b", "c" });
    }

    /**
     * Query must be like:
     * {
     * 	books
     * }
     */
    @QueryMapping
    public List<Book> books() {
        return List.of(
                new Book(1, "First book", new Author(3, "Bob")),
                new Book(2, "Second book", new Author(1, "Maxim")),
                new Book(3, "Third book", new Author(2, "Anna"))
        );
    }

    /**
     * If we want to specify fetching of the fields of the object (type)
     * Here we specify a selection of authors from books
     */
    @SchemaMapping(typeName = "Book")
    public Author author(Book book) {
        // return null; // we can return any value
        return book.author();
    }

//    /**
//     * Same as the previous method, but allows GraphQL to download authors in one batch,
//     * which reduces the load on the server and helps to avoid the N+1 query problem.
//     */
//    @BatchMapping
//    public Map<Book, Author> author(Collection<Book> books) {
//        return books.stream()
//                .collect(Collectors.toMap(Function.identity(), Book::author));
//    }
}
