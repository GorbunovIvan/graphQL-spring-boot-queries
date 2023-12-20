package org.example.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Random;

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
}
