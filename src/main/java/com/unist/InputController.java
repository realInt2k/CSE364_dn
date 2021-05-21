package com.unist;

import org.springframework.web.bind.annotation.*;

@RestController
public class InputController {
    @GetMapping("/users/recommendations")
    public String getInput(@RequestBody String inp) {
        System.out.println(inp);
        return inp;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!\n";
    }
}

