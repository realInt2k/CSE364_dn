package com.unist;

import com.data.*;
import com.help.UserDir;

import java.io.FileNotFoundException;

public class Milestone1 {
    Reviewer[] reviewers;
    Movie[] movies;
    Rating[] ratings;

    public void initialize() throws FileNotFoundException {
        FileReader reader = new FileReader();
        UserDir ud = new UserDir();
        // read user.dat
        String[] lines = reader.readFile(ud.get() + "/data/users.dat").clone();
        reviewers = new Reviewer[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            reviewers[i] = new Reviewer();
            reviewers[i].input(lines[i]);
        }
        // read movies.dat
        lines = reader.readFile(ud.get() + "/data/movies.dat").clone();
        movies = new Movie[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            movies[i] = new Movie();
            movies[i].input(lines[i]);
        }
        // read ratings.dat
        lines = reader.readFile(ud.get() + "/data/ratings.dat").clone();
        ratings = new Rating[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            ratings[i] = new Rating();
            ratings[i].input(lines[i]);
        }
    }

    public int output() {
        return 0;
    }
}
