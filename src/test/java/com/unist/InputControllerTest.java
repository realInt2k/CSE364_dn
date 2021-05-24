package com.unist;

import org.junit.Assert;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InputControllerTest {
    /*
    Spring interprets the @Autowired annotation,
    and the controller is injected before the test methods are run
     */
    @Autowired
    private InputController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void output() {
    }

    @Test
    void getUsersRecommendationsInput() throws IOException {

    }

    @Test
    void getUsersRecommendedMovieTitle() {
    }

    @Test
    void index() {
    }
}