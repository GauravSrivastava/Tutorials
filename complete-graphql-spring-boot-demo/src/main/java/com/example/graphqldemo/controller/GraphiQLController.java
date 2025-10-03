package com.example.graphqldemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to serve the GraphiQL interface
 */
@Controller
public class GraphiQLController {

    @GetMapping("/graphiql")
    public String graphiql() {
        return "forward:/graphiql/index.html";
    }
}
