package com.data;

import com.help.UserDir;
import com.unist.FileReaderBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

public class Universal {
    public static Rating[] ratings;
    public static Reviewer[] reviewers;
    public static Movie[] movies;
    public static String[] genres; //a.k.a categories
    public static String[] occupation;
    public static Map<Integer, String> moviePoster = new HashMap<>();
    public static Map<String, Integer> parseOccupation = new HashMap<>();
    public static Map<Integer, Reviewer> reviewerMap = new HashMap<>();
    public static Map<Integer, Movie> movieMap = new HashMap<>();
    public static Person customer = new Person();
    public static Link[] links;
    public static Map<Integer, String> movieImdbID = new HashMap<>();
    // counting number of ratings for each movieID
    public static Map<Integer, Integer> movieCnt = new HashMap<>();
    public static Map<Integer, Double> movieAvgScore = new HashMap<>();

    public static void readOccupation(BufferedReader br) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        String[] lines = reader.readFile(br);
        occupation = new String[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            String[] subLine = lines[i].split(":").clone();
            occupation[i] = subLine[1];
            parseOccupation.put(subLine[1], Integer.parseInt(subLine[0]));
        }
    }

    public static void readGenres(BufferedReader br) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        String[] lines = reader.readFile(br);
        genres = new String[lines.length];
        System.arraycopy(lines, 0, genres, 0, lines.length);
    }

    public static void readMoviePoster(BufferedReader br) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        String[] lines = reader.readFile(br);
        for (int i = 0; i < lines.length; ++i) {
            String[] subline = lines[i].split(",").clone();
            moviePoster.put(Integer.parseInt(subline[0]), subline[1]);
        }
    }

    public static void readMovie(BufferedReader br) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        String[] lines = reader.readFile(br);
        Movie[] movies_ = new Movie[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            movies_[i] = new Movie();
            movies_[i].input(lines[i]);
        }
        movies = movies_.clone();
        for (Movie movie : movies) {
            movieMap.put(movie.ID, movie);
        }
    }

    public static void readReviewer(BufferedReader br) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        String[] lines = reader.readFile(br);
        Reviewer[] reviewers_ = new Reviewer[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            reviewers_[i] = new Reviewer();
            reviewers_[i].input(lines[i]);
        }
        reviewers = reviewers_.clone();
        for (Reviewer reviewer : reviewers) {
            reviewerMap.put(reviewer.ID, reviewer);
        }
    }

    public static void readRating(BufferedReader br) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        String[] lines = reader.readFile(br);
        Rating[] ratings_ = new Rating[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            ratings_[i] = new Rating();
            ratings_[i].input(lines[i]);
        }
        ratings = ratings_.clone();
    }

    public static void readLink(BufferedReader br) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        String[] lines = reader.readFile(br);
        Link[] links_ = new Link[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            links_[i] = new Link();
            links_[i].input(lines[i]);
        }
        links = links_.clone();
        for (Link link : links) {
            movieImdbID.put(link.movieID, link.imdbID);
        }
    }

    public static void prepareData() throws IOException {
        ratings = ReadRatingData.data("/data/ratings.dat").clone();
        reviewers = ReadReviewerData.data("/data/users.dat").clone();
        movies = ReadMovieData.data("/data/movies.dat").clone();
        links = ReadLinkData.data("/data/links.dat").clone();
        for (Movie movie : movies) {
            movieMap.put(movie.ID, movie);
        }
        for (Reviewer reviewer : reviewers) {
            reviewerMap.put(reviewer.ID, reviewer);
        }
        for (Link link : links) {
            movieImdbID.put(link.movieID, link.imdbID);
        }
        FileReaderBuffer reader = new FileReaderBuffer();
        String[] lines;
        // initialize occupation parser
        lines = reader.readFile(UserDir.get() + "/data/occupations.dat").clone();
        occupation = new String[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            String[] subLine = lines[i].split(":").clone();
            occupation[i] = subLine[1];
            parseOccupation.put(subLine[1], Integer.parseInt(subLine[0]));
        }
        // read all available genres
        lines = reader.readFile(UserDir.get() + "/data/genres.dat").clone();
        genres = new String[lines.length];
        System.arraycopy(lines, 0, genres, 0, lines.length);

        lines = reader.readFile(UserDir.get() + "/data/movie_poster.csv").clone();
        for (int i = 0; i < lines.length; ++i) {
            String[] subline = lines[i].split(",").clone();
            moviePoster.put(Integer.parseInt(subline[0]), subline[1]);
        }
    }
    /*count number of rating for each movies*/
    public static void countMovie() {
        for(Rating rate:ratings) {
            if(!movieCnt.containsKey(rate.movieId)) {
                movieCnt.put(rate.movieId, 1);
            } else {
                int cnt = movieCnt.get(rate.movieId);
                movieCnt.replace(rate.movieId, cnt + 1);
            }
        }
        for(Movie movie:movies) {
            if(!movieCnt.containsKey(movie.ID)) {
                movieCnt.put(movie.ID, 0);
            }
        }
    }

    public static void calculateAvgScore() {
        for(Rating rate:ratings) {
            int cnt = movieCnt.get(rate.movieId);
            if(cnt == 0) {
                movieAvgScore.put(rate.movieId, 0.0);
                continue;
            }
            if(!movieAvgScore.containsKey(rate.movieId)) {
                movieAvgScore.put(rate.movieId, (double)rate.rating/(double)cnt);
            } else {
                Double sum = movieAvgScore.get(rate.movieId);
                Double newSum = (sum + (double)rate.rating/(double)cnt);
                movieAvgScore.replace(rate.movieId, newSum);
            }
        }
        for(Movie movie:movies) {
            if(!movieAvgScore.containsKey(movie.ID)) {
                movieAvgScore.put(movie.ID, (double)0);
            }
        }
    }
}
