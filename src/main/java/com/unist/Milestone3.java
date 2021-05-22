package com.unist;

import com.data.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Milestone3 {
    public Movie[] movies;
    public Movie userMovie;
    public String title = "";
    public int limit = 10;
    public boolean badArg = false;
    public String badArgMsg = "";
    public boolean warning = false;
    public String warningMsg = "";

    public void setBadArg(String e) {
        this.badArg = true;
        if(e!=null)
            this.badArgMsg = e;
    }

    public void setWarning(String w) {
        this.warning = true;
        if(w!=null)
            this.warningMsg += w;
    }

    Milestone3() {}
    public void parseArg1(String[] args) {
        if(args[1] != null)
        {
            int lim = 0;
            try {
                lim = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                this.setBadArg("limit is not an integer\n");
                return;
            }
            if(lim > Universal.movies.length) {
                this.setBadArg("movie limit input by user > number of movies in database\n");
            } else if(lim < 0)
            {
                this.setBadArg("movie limit input by user is negative\n");
            } else {
                this.limit = Integer.parseInt(args[1]);
            }
        }
    }
    public void parseArg0(String[] args) {
        title = args[0];
        if(title == null) {
            title = "";
            setWarning("No title argument found\n");
        }
    }
    Milestone3(String[] args) {
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
        if(res == null) {
            this.setWarning("No such movie found, recommending top "+limit+" any movie instead\n");
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
    public JSONObject[] findSimilar(Movie mv) throws JSONException {
        JSONObject[] res = null;
        this.movies = Universal.movies.clone();
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
                    .put("title", this.movies[i].title);
        }
        return res;
    }
    public JSONObject[] solve() throws JSONException {
        if(this.badArg) {
            return new JSONObject[]{new JSONObject().put("bad arg",this.badArgMsg)};
        }
        JSONObject[] res = null;
        userMovie = findMovie(this.title);
        res = findSimilar(userMovie);
        return res;
    }
}
