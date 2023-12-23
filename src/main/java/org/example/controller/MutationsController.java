package org.example.controller;

import org.example.model.Book;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutations:<br>
 *  - used for write operations that modify or create data on the server.<br>
 *  - designed to perform operations that have side effects, such as creating, updating, or deleting data.<br>
 *  - mutations are explicitly defined to indicate that they might change the state of the server.<br>
 *
 *  <p>But technically you are not prohibited from using "queries" for write operations instead of "mutations",
 *  but best practices strongly recommend using "mutations" for this.</p>
 */
@Controller
public class MutationsController {

    List<Book> books = new ArrayList<>();

    /**
     * Query must be like:
     * mutation {
     *   addBook(name: "Maxim") {
     *     id
     *     name
     *   }
     * }
     */
    @MutationMapping
//    @SchemaMapping(typeName = "Mutation", field = "addBook") // - just the same as "@MutationMapping"
    public Book addBook(@Argument String name) {
        var book = new Book(books.size()+1, name, null);
        books.add(book);
        return book;
    }
}
