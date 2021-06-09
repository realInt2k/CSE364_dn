package com.unist.webApp;

import com.data.Movie;
import com.data.Universal;
import com.mongodb.util.JSON;
import com.unist.Milestone2;
import com.unist.Milestone3;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class InputController {

    public final MovieDAL movieDAL;
    public final MovieRepository movieRepository;

    public InputController(MovieRepository movieRepository, MovieDAL movieDAL) {
        //System.out.println("IT goes to yes constructor");
        this.movieDAL = movieDAL;
        this.movieRepository = movieRepository;
        this.movieRepository.save(Arrays.asList(Universal.movies));
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String frontPage() throws JSONException {
        JSONObject res = new JSONObject().put("id", 2).put("content", "RESTapi");
        return res.toString();
    }

    @RequestMapping(value="/movies", method = RequestMethod.GET)
    public String listAllMovies() throws JSONException {
        movieRepository.deleteAll();
        this.movieRepository.save(Arrays.asList(Universal.movies));
        List<Movie> arr = movieDAL.getAllMovies();
        JSONObject[] res = new JSONObject[arr.size()];
        for(int i = 0; i < arr.size(); ++i) {
            res[i] = new JSONObject()
                    .put("imdb", "http://www.imdb.com/title/tt" + Universal.movieImdbID.get(arr.get(i).ID))
                    .put("genre", arr.get(i).genre())
                    .put("title", arr.get(i).title);
        }
        return output(res);
    }

    public String output(JSONObject[] objs) throws JSONException {
        ArrayList<String> output = new ArrayList<>();
        for(JSONObject js: objs) {
            output.add(js.toString());
        }
        String consoleOutput = null;
        consoleOutput = output.toString();
        return consoleOutput;
    }
//    public String gender = null, age = null, occupation = null;
//    public String genre = null;
    @RequestMapping("/users/recommendations")
    public String getUsersRecommendationsInput(
            @RequestParam(value = "gender", defaultValue = "") String gender,
            @RequestParam(value = "age", defaultValue = "") String age,
            @RequestParam(value = "occupation", defaultValue = "") String occupation,
            @RequestParam(value = "genres", defaultValue = "") String genres
    ) throws IOException, JSONException {
        Argument1 inp = new Argument1();
        inp.gender = gender;
        inp.age = age;
        inp.occupation = occupation;
        inp.genres = genres;
        Milestone2 mile2 = new Milestone2(inp.args());
        JSONObject[] objs = mile2.solve();
        JSONObject bigOutput = new JSONObject();
        bigOutput.put("movieList", objs);
        bigOutput.put("warning", mile2.extraMsg.warningMsg);
        return bigOutput.toString();
    }

    @RequestMapping("custom/users/recommendations")
    public String getCustomUsersRecommendationsInput(
            @RequestParam(value = "gender", defaultValue = "") String gender,
            @RequestParam(value = "age", defaultValue = "") String age,
            @RequestParam(value = "occupation", defaultValue = "") String occupation,
            @RequestParam(value = "genres", defaultValue = "") String genres
    ) throws IOException, JSONException {
        Argument1 inp = new Argument1();
        inp.gender = gender;
        inp.age = age;
        inp.occupation = occupation;
        inp.genres = genres;
        Milestone2 mile2 = new Milestone2(inp.args());
        JSONObject[] objs = mile2.customSolve();
        JSONObject bigOutput = new JSONObject();
        bigOutput.put("movieList", objs);
        bigOutput.put("warning", mile2.extraMsg.warningMsg);
        return bigOutput.toString();
    }

    @RequestMapping("/movies/recommendations")
    public String getUsersRecommendedMovieTitle(@RequestBody Argument2 inp) throws IOException, JSONException {
        Milestone3 mile3 = new Milestone3(inp.args());
        JSONObject[] objs = mile3.solve();
        if(mile3.extraMsg.hasWarning())
            return mile3.extraMsg.warningMsg + output(objs);
        else
            return output(objs);
    }

}

