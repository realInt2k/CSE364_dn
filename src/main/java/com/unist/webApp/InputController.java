package com.unist.webApp;

import com.data.Movie;
import com.data.Rating;
import com.data.Universal;
import com.help.UserDir;
import com.mongodb.util.JSON;
import com.unist.FileReaderBuffer;
import com.unist.Milestone2;
import com.unist.Milestone3;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

@RestController
public class InputController extends HttpServlet {

    public final MovieDAL movieDAL;
    public final RatingDAL ratingDAL;
    public final MovieRepository movieRepository;
    public final RatingRepository ratingRepository;

    public void readEveryThing() throws IOException {
        //OCCUPATION;
        System.out.println("\n\n\n\n\n" + "OCCUPATION" + "\n\n\n\n");
        InputStream input = getClass().getClassLoader().getResourceAsStream("/data/occupations.dat");
        assert input != null;
        BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        Universal.readOccupation(br);
        //Genres
        System.out.println("\n\n\n\n\n" + "GENRES" + "\n\n\n\n");
        input = getClass().getClassLoader().getResourceAsStream("/data/genres.dat");
        assert input != null;
        br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        Universal.readGenres(br);
        //Movie Poster
        System.out.println("\n\n\n\n\n" + "MOVIE POSTER" + "\n\n\n\n");
        input = getClass().getClassLoader().getResourceAsStream("/data/movie_poster.csv");
        assert input != null;
        br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        Universal.readMoviePoster(br);
        //Read Movie
        System.out.println("\n\n\n\n\n" + "MOVIE" + "\n\n\n\n");
        input = getClass().getClassLoader().getResourceAsStream("/data/movies.dat");
        assert input != null;
        br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        Universal.readMovie(br);
        //Read reviewer
        System.out.println("\n\n\n\n\n" + "REVIEWER" + "\n\n\n\n");
        input = getClass().getClassLoader().getResourceAsStream("/data/users.dat");
        assert input != null;
        br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        Universal.readReviewer(br);
        //Read rating
        System.out.println("\n\n\n\n\n" + "RATING" + "\n\n\n\n");
        input = getClass().getClassLoader().getResourceAsStream("/data/ratings.dat");
        assert input != null;
        br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        Universal.readRating(br);
        //Read Links
        System.out.println("\n\n\n\n\n" + "LINK" + "\n\n\n\n");
        input = getClass().getClassLoader().getResourceAsStream("/data/links.dat");
        assert input != null;
        br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        Universal.readLink(br);
        // Do other stuffs
        Universal.countMovie();
        Universal.calculateAvgScore();
    }

    public InputController(MovieRepository movieRepository, MovieDAL movieDAL,
                           RatingRepository ratingRepository, RatingDAL ratingDAL) throws IOException {
        //System.out.println("IT goes to yes constructor");
        this.movieDAL = movieDAL;
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.ratingDAL = ratingDAL;
        System.out.println("\n\n\n\n\n" + (ratingRepository != null) + " " + (ratingDAL != null) + "   " + UserDir.get() + "\n\n\n\n");
        if(Universal.movies == null) {
            readEveryThing();
        }
        this.movieRepository.saveAll(Arrays.asList(Universal.movies));
    }

    @RequestMapping(value="/")
    public String frontPage(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        //JSONObject res = new JSONObject().put("id", 2).put("content", "RESTapi");
        //sendRedirect("/index.html");
        //return res.toString();
        response.sendRedirect("/index.html");
        return "redirect:/index.html";
    }

    @RequestMapping(value="/movies", method = RequestMethod.GET)
    public String listAllMovies() throws JSONException {
        //movieRepository.deleteAll();
        //this.movieRepository.save(Arrays.asList(Universal.movies));
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

    @RequestMapping(value="/ratings", method = RequestMethod.GET)
    public String listAllRatings() throws JSONException {
        //movieRepository.deleteAll();
        //this.movieRepository.save(Arrays.asList(Universal.movies));
        List<Rating> arr = ratingDAL.getAllRating();
        //JSONObject[] res = new JSONObject[arr.size()];
        return arr.toString();
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
        System.out.println(genres);
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
        genres = genres.replaceAll("_", "\\|");
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
    public String getUsersRecommendedMovieTitle(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "limit", defaultValue = "") String limit
    ) throws IOException, JSONException {
        Argument2 inp = new Argument2();
        inp.title = title;
        inp.limit = limit;
        Milestone3 mile3 = new Milestone3(inp.args());
        JSONObject[] objs = mile3.solve();
        JSONObject bigOutput = new JSONObject();
        bigOutput.put("movieList", objs);
        bigOutput.put("warning", mile3.extraMsg.warningMsg);
        return bigOutput.toString();
    }

}

