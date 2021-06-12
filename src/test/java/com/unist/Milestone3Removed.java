package com.unist;

import com.data.Movie;
import com.data.Universal;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Milestone3Removed {
    @BeforeEach
    void init() throws IOException {
        Universal.prepareData();
        Universal.countMovie();
        Universal.calculateAvgScore();
    }
    @Test
    void parseArg1() {
        String title = "Requiem for a Dream (2000)";
        String limit = "100000";
        String[] args = new String[] {title, limit};
        Milestone3 t1 = new Milestone3(args);
        assertEquals(t1.extraMsg.hasBadArg(), true);
        limit = "-12312";
        args[1] = limit;
        Milestone3 t2 = new Milestone3(args);
        assertEquals(t2.extraMsg.hasBadArg(), true);
        args[1] = null;
        Milestone3 t3 = new Milestone3(args);
        assertEquals(t3.limit, 10);
        args[0] = "Requiem a Dream (2000)";
        args[1] = "50";
        Milestone3 t4 = new Milestone3(args);
        assertEquals(t4.limit, 50);
    }

    @Test
    void parseArg0() throws JSONException {
        String title = "Requiem for a Dream (2000)";
        String[] args = new String[] {"Requiem for a Dream (2000)", ""};
        Milestone3 t1 = new Milestone3(args);
        JSONObject[] res1 = t1.solve();
        assertEquals(t1.title, title);
        args[0] = null; args[1] = "11";
        Milestone3 t2 = new Milestone3(args);
        JSONObject[] res2 = t2.solve();
        assertEquals(t2.title, "");
        System.out.println(res2.toString());
        assertEquals(res2.length, 11);
    }

    @Test
    void findMovie() {
        String title = "Toy Story (1995";
        String limit = "5";
        String[] args = new String[] {title, limit};
        Milestone3 t1 = new Milestone3(args);
        Movie mv = t1.findMovie(title);
        assertEquals(mv, null);

        args[0] += ')';
        Milestone3 t2 = new Milestone3(args);
        mv = t2.findMovie(args[0]);
        assertEquals(mv.genre(), "Animation|Children's|Comedy");
    }

    @Test
    void compareGenreScore() throws JSONException {
        String title = "Toy Story (1995"; // userMovie == null
        String limit = "50";
        String[] args = new String[] {title, limit};
        Milestone3 t1 = new Milestone3(args);
        t1.solve();
        double score1 = t1.compareGenreScore(Universal.movies[1]);
        assertEquals(score1, (double)1.0);
        title += ")";
        args[0] = title;
        Milestone3 t2 = new Milestone3(args);
        t2.solve();
        double score2 = t2.compareGenreScore(Universal.movies[1]);
        assertEquals(t2.title, "Toy Story (1995)");
        assertEquals(Universal.movies[1].title, "Jumanji (1995)");
        assertEquals(score2, (double)1/3);
    }
}