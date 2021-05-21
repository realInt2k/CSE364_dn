package com.unist;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InputController {
    private final UserRepository repository;

    InputController(UserRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/users/recommendations")
    List<User> all() {
        return repository.findAll();
    }
    /*public String getInput(@RequestBody String inp) {
        System.out.println(inp);
        return inp;
    }*/

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!\n";
    }
}

