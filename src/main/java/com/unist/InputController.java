package com.unist;

import com.data.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

class argument1 {
    public String gender, age, occupation;
    public String genre;

    public void print() {
        System.out.println(this.gender + " " + this.age + " " + this.occupation +  " " + this.genre);
    }

    public String[] args() {
        String[] res = new String[4];
        res[0] = this.gender;
        res[1] = this.age;
        res[2] = this.occupation;
        res[3] = this.genre;
        return res;
    }
}

class argument2 {
    public String title = null;
    public String limit = null;

    public void print() {
        System.out.println(this.title + " " + this.limit);
    }

    public String[] args() {
        String[] res = new String[2];
        res[0] = this.title;
        res[1] = this.limit;
        return res;
    }
}

@RestController
public class InputController {

    public String output(JSONObject[] objs) throws JSONException {
        ArrayList<String> output = new ArrayList<>();
        for(JSONObject js: objs) {
            output.add(js.toString(4));
        }
        String consoleOutput = null;
        consoleOutput = output.toString();
        return consoleOutput;
    }

    @GetMapping("/users/recommendations")
    public String getUsersRecommendationsInput(@RequestBody argument1 inp) throws IOException, JSONException {
        Milestone2 mile2 = new Milestone2(inp.args());
        JSONObject[] objs = mile2.solve();
        return output(objs);
    }

    @GetMapping("/movies/recommendations")
    public String getUsersRecommendedMovieTitle(@RequestBody argument2 inp) throws IOException, JSONException {
        Milestone3 mile3 = new Milestone3(inp.args());
        JSONObject[] objs = mile3.solve();
        return output(objs);
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Group 5\n";
    }
}

