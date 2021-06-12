package com.unist;

import com.data.Universal;

import static org.assertj.core.api.Assertions.*;

import com.unist.webApp.InputController;
import com.unist.webApp.Argument1;
import com.unist.webApp.Argument2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class InputControllerRemoved {
    /*
    Spring interprets the @Autowired annotation,
    and the controller is injected before the test methods are run
     */
    @Autowired
    private InputController controller;

    @Autowired
    private MockMvc mockMvc;

    //@Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    //@Test
    void output() {
    }

    @Test
    void getUsersRecommendationsInput() throws Exception {
        Universal.prepareData();
        Universal.countMovie();
        Universal.calculateAvgScore();
        Argument1 object = new Argument1();
        object.age = "12";
        object.gender = "F";
        object.genre = "Action|War";
        object.occupation = "gradstudent";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json1 = ow.writeValueAsString(object);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/users/recommendations")
                .content(json1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        
        object.gender = "";
        object.genres = "Drama";
        ObjectWriter ow1 = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json2 = ow1.writeValueAsString(object);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/users/recommendations")
                .content(json2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        object.occupation = "Pro CSGO player";
        ObjectWriter ow2 = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json3 = ow2.writeValueAsString(object);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/users/recommendations")
                .content(json3)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        object.genres = "";
        ObjectWriter ow3 = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json4 = ow3.writeValueAsString(object);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/users/recommendations")
                .content(json4)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void getUsersRecommendedMovieTitle() throws Exception {
        Universal.prepareData();
        Universal.countMovie();
        Universal.calculateAvgScore();
        Argument2 object = new Argument2();
        object.title = "toy";
        object.limit = "10";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/movies/recommendations")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        
        object.title = "Toy Story (1995)";
        ObjectWriter ow1 = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json1 = ow1.writeValueAsString(object);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/movies/recommendations")
                .content(json1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        object.limit = "-1";
        ObjectWriter ow2 = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json2 = ow2.writeValueAsString(object);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/movies/recommendations")
                .content(json2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        object.limit = "0";
        ObjectWriter ow3 = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json3 = ow3.writeValueAsString(object);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/movies/recommendations")
                .content(json3)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        object.limit = "10000";
        ObjectWriter ow4 = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json4 = ow4.writeValueAsString(object);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/movies/recommendations")
                .content(json4)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void index() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Greetings from Group 5")));
    }
}
