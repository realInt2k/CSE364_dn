package com.unist.webBackend;

import com.unist.Milestone2;
import com.unist.Milestone3;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class InputController {

    public String output(JSONObject[] objs) throws JSONException {
        ArrayList<String> output = new ArrayList<>();
        for(JSONObject js: objs) {
            output.add(js.toString(4));
        }
        String consoleOutput = null;
        consoleOutput = output.toString();
        return consoleOutput + "\n";
    }

    @RequestMapping("/users/recommendations")
    public String getUsersRecommendationsInput(@RequestBody argument1 inp) throws IOException, JSONException {
        Milestone2 mile2 = new Milestone2(inp.args());
        JSONObject[] objs = mile2.solve();
        if(mile2.extraMsg.hasWarning())
            return mile2.extraMsg.warningMsg + output(objs) ;
        else
            return output(objs);
    }

    @RequestMapping("/movies/recommendations")
    public String getUsersRecommendedMovieTitle(@RequestBody argument2 inp) throws IOException, JSONException {
        Milestone3 mile3 = new Milestone3(inp.args());
        JSONObject[] objs = mile3.solve();
        if(mile3.extraMsg.hasWarning())
            return mile3.extraMsg.warningMsg + output(objs);
        else
            return output(objs);
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Group 5\n";
    }
}

