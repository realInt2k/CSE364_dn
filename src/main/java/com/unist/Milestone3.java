package com.unist;

import com.data.*;
import com.errorAndWarning.JSONEW_1;
import com.errorAndWarning.JSONErrorAndWarning;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Milestone3 {
    public Movie[] movies;
    public Movie userMovie;
    public String title = "";
    public int limit = 10;
    public JSONErrorAndWarning extraMsg = new JSONEW_1();

    public void parseArg1(String[] args) {

        if(args[1] != null)
        {
            if(args[1].isEmpty()) {
                return;
            }
            int lim = 0;
            try {
                lim = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                this.extraMsg.setBadArg("limit is not an integer\n");
                return;
            }
            int length = Universal.movies.length;
            if(this.findMovie(this.title) != null)
                length -= 1;
            if(lim > length) {
                this.extraMsg.setBadArg("movie limit input by user > number of movies in database\n");
            } else if(lim < 0)
            {
                this.extraMsg.setBadArg("movie limit input by user is negative\n");
            } else {
                this.limit = Integer.parseInt(args[1]);
            }
        }
    }
    public void parseArg0(String[] args) {
        title = args[0];
        if(title == null) {
            title = "";
            this.extraMsg.setWarning("No title argument found\n");
        }
    }
    public Milestone3(String[] args) {
        this.parseArg0(args);
        this.parseArg1(args);
    }
    public Movie findMovie(String title) {
        Movie res = null;
        String modTitle = title.replaceAll("\\s+","");
        for(int i = 0; i < Universal.movies.length; ++i) {
            if(modTitle.equalsIgnoreCase(Universal.movies[i].title.replaceAll("\\s+",""))) {
                res = Universal.movies[i];
                break;
            }
        }
        return res;
    }
    public double compareGenreScore(Movie x) {
        if(userMovie == null) { // solely dependent on score
            return 1.0;
        }
        double cnt = 0.0;
        for(String genre1 : userMovie.cat) {
            for(String genre2 : x.cat) {
                if(genre1.equalsIgnoreCase(genre2))
                    cnt += 1.0;
            }
        }
        return cnt / (double)userMovie.cat.length;
    }

    public void filter(Movie mv) {
        ArrayList<Movie> tmp = new ArrayList<>();
        if(mv == null)
            this.movies = Universal.movies.clone();
        else {
            for(Movie m : Universal.movies) {
                if(!mv.equals(m)) {
                    tmp.add(m);
                }
            }
            this.movies = new Movie[tmp.size()];
            tmp.toArray(this.movies);
        }
    }
    public JSONObject[] findSimilar(Movie mv) throws JSONException {
        JSONObject[] res = null;
        this.filter(mv);
        Arrays.sort(this.movies, (o1, o2) -> {
            double genreSimilarityO1 = this.compareGenreScore(o1);
            double genreSimilarityO2 = this.compareGenreScore(o2);
            int cmpScore = Double.compare(genreSimilarityO2, genreSimilarityO1);
            if(cmpScore != 0) {
                return cmpScore;
            } else {
                double rateO1 = Universal.movieAvgScore.get(o1.ID);
                double rateO2 = Universal.movieAvgScore.get(o2.ID);
                return Double.compare(rateO2, rateO1);
            }
        });
        res = new JSONObject[this.limit];
        for(int i = 0; i < this.limit; ++i) {
            res[i] = new JSONObject()
                    .put("imdb","http://www.imdb.com/title/tt" + Universal.movieImdbID.get(this.movies[i].ID))
                    .put("genre", this.movies[i].genre())
                    .put("title", this.movies[i].title)
                    .put("imageLink", Universal.moviePoster.get(this.movies[i].ID))
                    .put("id", this.movies[i].ID);
        }
        return res;
    }
    public JSONObject[] solve() throws JSONException {
        if(this.extraMsg.hasBadArg()) {
            return new JSONObject[]{new JSONObject().put("arg fault",this.extraMsg.badArgMsg)};
        }
        JSONObject[] res = null;
        userMovie = findMovie(this.title);
        if(userMovie == null) {
            this.extraMsg.setWarning("No such movie found, recommending top "+limit+" any movie instead\n");
        }
        res = findSimilar(userMovie);
        return res;
    }
}
