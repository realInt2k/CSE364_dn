package com.unist;
import com.data.*;
import com.help.UserDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

class ReviewerCmpID implements Comparator<Reviewer> {
    @Override
    public int compare(Reviewer r1, Reviewer r2) {
        return r1.ID - r2.ID;
    }
}

public class Milestone1 {
    Reviewer[] reviewers;
    Movie[] movies;
    Rating[] ratings;
    String[] genres; //a.k.a categories
    String[] occupation;

    double finalResult;
    Map<String, Integer> parseOccupation = new HashMap<>();
    Map<Integer, Reviewer> reviewerMap = new HashMap<>();
    Map<Integer, Movie> movieMap = new HashMap<>();

    String [] removeDuplicateCat(String [] cat) {
        List<String> tmp = new ArrayList<String>();
        for(int i = 0; i < cat.length; ++i) {
            tmp.add(cat[i]);
        }
        int i = 0;
        while(i < tmp.size()) {
            int j = i + 1;
            while(j < tmp.size()) {
                if(tmp.get(i).equalsIgnoreCase(tmp.get(j))) {
                    tmp.remove(j);
                } else j ++;
            }
            i ++;
        }
        String [] newCatList = new String[tmp.size()];
        tmp.toArray(newCatList);
        return newCatList;
    }

    public void solve(String[] arg) {
        String occupationStr = "no occupation";
        int occupation = 0;
        String[] categories = null;
        categories = arg[0].split("\\|").clone();
        categories = removeDuplicateCat(categories).clone();
        if (!genre_check(categories)){
            this.finalResult = -1;
            return;
        }

        if (arg.length > 1) {
            occupationStr = arg[1].toLowerCase();
            if(occupationStr != "other" && parseOccupation.containsKey(occupationStr)) {
                occupation = parseOccupation.get(occupationStr);
            }
            else {
                // Error
                // Given occupation does not exist
                System.out.format("No such occupation: %s%n", occupationStr);
                System.out.format("Try one of these: %n");
                print_occupation();
                this.finalResult = -1;
                return;
            }
        }  else {
            System.out.format("No occupation argument specified, 'other' will be selected as default");
            occupationStr = "other";
        }

        long score = 0;
        long nMatches = 0;
        
        System.out.format("%nCalculating score for '%s' according to these genres: %n",
                            occupationStr);
        for (int i = 0; i < categories.length; ++i) {
            System.out.print(categories[i] + " ");
        }
        System.out.println("");

        for(int i = 0; i < ratings.length; ++i) {
            int userId = ratings[i].userId;
            if(reviewerMap.get(userId).occupation != occupation) continue;
            int movieId = ratings[i].movieId;
            Movie movie = movieMap.get(movieId);
            int match = 0;

            for(int j = 0; j < categories.length; ++j)
            {
                boolean flag = false;
                for(int k = 0; k < movie.cat.length; ++k)
                    if(movie.cat[k].equalsIgnoreCase(categories[j])) {
                        flag = true;
                        break;
                    }
                if(flag)
                    ++match;
            }
            if(match == categories.length) {
                score += ratings[i].rating;
                nMatches ++;
            }
        }
        System.out.format("Found %d matches total%n", nMatches);
        if(nMatches == 0) {
            this.finalResult = 0;
        } else
            this.finalResult = (1.0 * score)/ (1.0 * nMatches);
    }
    public void initialize() throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        UserDir ud = new UserDir();
        // read user.dat
        String[] lines = reader.readFile(ud.get() + "/data/users.dat").clone();
        reviewers = new Reviewer[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            reviewers[i] = new Reviewer();
            reviewers[i].input(lines[i]);
            reviewerMap.put(reviewers[i].ID, reviewers[i]);
        }
        // read movies.dat
        lines = reader.readFile(ud.get() + "/data/movies.dat").clone();
        movies = new Movie[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            movies[i] = new Movie();
            movies[i].input(lines[i]);
            movieMap.put(movies[i].ID, movies[i]);
        }
        // read ratings.dat
        lines = reader.readFile(ud.get() + "/data/ratings.dat").clone();
        ratings = new Rating[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            ratings[i] = new Rating();
            ratings[i].input(lines[i]);
        }
        // initialize occupation parser
        lines = reader.readFile(ud.get() + "/data/occupations.dat").clone();
        occupation = new String[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            String[] subLine = lines[i].split(":").clone();
            occupation[i] = subLine[1];
            parseOccupation.put(subLine[1], Integer.parseInt(subLine[0]));
        }
        // read all available genres
        lines = reader.readFile(ud.get() + "/data/genres.dat").clone();
        genres = new String[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            genres[i] = lines[i];
        }
    }

    public void print_occupation() {
        for(int i = 0; i < occupation.length; ++i) {
            System.out.format("- %s\n", occupation[i]);
        }
    }

    public void print_genres() {
        for(int i = 0; i < genres.length; ++i) {
            System.out.format("- %s\n", genres[i]);
        }
    }

    public boolean genre_check(String [] categories){
        for (int i = 0; i < categories.length; i++){
            for (int j = 0; j < genres.length; j++) {
                if (categories[i].equalsIgnoreCase(genres[j])){
                    j = genres.length + 2;
                }
                if (j == genres.length - 1){
                    System.out.format("No such genre as \"%s\"%n", categories[i]);
                    System.out.format("Try one of these: %n");
                    print_genres();
                    return false;
                }
            }
        }

        return true;
    }

    public double output() {
        return this.finalResult;
    }
}
