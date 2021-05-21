package com.unist;

import com.data.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

class argument {
    public String gender, age, occupation;
    public String genre;

    public void print() {
        System.out.println(this.gender + " " + this.age + " " + this.occupation +  " " + this.genre);
    }
}

@RestController
public class InputController {
    @GetMapping("/users/recommendations")
    public String getUsersRecommendationsInput(@RequestBody argument inp) throws IOException, JSONException {
        inp.print();
        String[] args = new String[4];
        args[0] = inp.gender;
        args[1] = inp.age;
        args[2] = inp.occupation;
        args[3] = inp.genre;
        Milestone2 mile2 = new Milestone2(args);
        JSONObject[] objs = mile2.solve();
        ArrayList<String> output = new ArrayList<>();
        for(JSONObject js: objs) {
            output.add(js.toString(4));
            System.out.println(js.toString());
        }
        String consoleOutput = null;
        consoleOutput = output.toString();
        return consoleOutput;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!\n";
    }
}

