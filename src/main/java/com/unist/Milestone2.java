package com.unist;

import com.data.*;
import com.help.RelevanceScore;
import com.help.UserDir;

import java.io.IOException;
import java.util.*;

public class Milestone2 {
    public Rating[] ratings;
    public Reviewer[] reviewers;
    public Movie[] movies;
    public String[] genres; //a.k.a categories
    public String[] occupation;
    public Map<String, Integer> parseOccupation = new HashMap<>();
    public Map<Integer, Reviewer> reviewerMap = new HashMap<>();
    public Map<Integer, Movie> movieMap = new HashMap<>();
    public Person customer = new Person();
    public Link[] links;

    private boolean badArgs = false;
    private Map<Integer, String> movieImdbID = new HashMap<>();
    private Map<Integer, Float> movieRelevantScore = new HashMap<>();
    private Map<Integer, Float> movieAvgScore = new HashMap<>();
    private Map<Integer, Integer> movieCnt = new HashMap<>();

    public Milestone2() {}
    public void prepareData() throws IOException {
        this.ratings = ReadRatingData.data("/data/ratings.dat").clone();
        this.reviewers = ReadReviewerData.data("/data/users.dat").clone();
        this.movies = ReadMovieData.data("/data/movies.dat").clone();
        this.links = ReadLinkData.data("/data/links.dat").clone();
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
        UserDir ud = new UserDir();
        String[] lines;
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
        System.arraycopy(lines, 0, genres, 0, lines.length);
    }
    private void badArgsExit(String problem) {
        System.out.println(problem);
    }
    public Milestone2(String[] args) throws IOException {
        if(args.length < 3 || args.length > 4) {
            System.out.println("arguments length incorrect, please follow this format: " +
                    "\"Gender\" " +
                    "\"Age\" " +
                    "\"Occupation\"");
            this.badArgs = true;
            return;
        }
        this.prepareData();
        String welcome = "";
        welcome += "Finding movies for a customer with: \n\n";
        if(!args[0].isEmpty()) {
            if(args[0].charAt(0) != 'F' && args[0].charAt(0) != 'M') {
                this.badArgs = true;
                this.badArgsExit("Gender is not \"F\" or \"M\"");
                return;
            }
            customer.setGender(args[0].charAt(0));
            welcome += "\tgender: " + customer.getGender() + '\n';
        } else {
           welcome += "\tgender: no preference \n";
        }
        if(!args[1].isEmpty()) {
            int age = 0;
            try {
                age = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                this.badArgs = true;
                this.badArgsExit("age input \"" + args[1] + "\" is incorrect, please check if they're all numeric, " +
                        "or contain whitespaces, etc.");
                return;
            }
            customer.setAge(age);
            welcome += "\tage: " + customer.getAge() + '\n';
        } else {
            welcome += "\tage: no preference\n";
        }
        boolean occupationNotFound=false;
        if(!args[2].isEmpty()) {
            if(parseOccupation.containsKey(args[2].toLowerCase())) {
                customer.setOccupation(parseOccupation.get(args[2].toLowerCase()));
            }
            else {
                occupationNotFound = true;
                customer.setOccupation(parseOccupation.get("other"));
            }
            welcome += "\toccupation: " + args[2] + '\n';
        } else {
            welcome += "\toccupation: no preference \n";
        }
        System.out.println();
        if(occupationNotFound)
            welcome += "No such occupation found, use 'other' as default\n";
        //customer.setAge();
        if(args.length == 4) {
            String[] genre;
            genre = args[3].split("\\|");
            customer.setGenre(genre);
        }
        System.out.println(welcome);
    }

    public float[] analyseRating(Person A) throws IOException {
        float[] score = new float[this.ratings.length];
        for (int i = 0; i < score.length; ++i) {
            Movie movie = movieMap.get(ratings[i].movieId);
            Reviewer reviewer = reviewerMap.get(ratings[i].userId);
            Person B = new Person();
            B.setAge(reviewer.age);
            B.setOccupation(reviewer.occupation);
            B.setGender(reviewer.gender);
            B.setGenre(movie.cat);
            score[i] = RelevanceScore.getScore(A, B);
        }
        return score;
    }

    public void calculateAvgScore() {
        for(Rating rate:ratings) {
            if(!movieCnt.containsKey(rate.movieId)) {
                movieCnt.put(rate.movieId, 1);
            } else {
                int cnt = movieCnt.get(rate.movieId);
                movieCnt.replace(rate.movieId, cnt+1);
            }
        }
        for(Rating rate:ratings) {
            if(!movieAvgScore.containsKey(rate.movieId)) {
                movieAvgScore.put(rate.movieId, (float)0);
            } else {
                float sum = movieAvgScore.get(rate.movieId);
                int cnt = movieCnt.get(rate.movieId);
                movieAvgScore.replace(rate.movieId, (sum + (float)rate.rating)/(float)cnt);
            }
        }
    }

    public Movie [] find_relevant_movies() throws IOException {
        float[] relevantScore = this.analyseRating(customer).clone();
        float maxRelevant = relevantScore[0];
        float minRelevant = relevantScore[0];
        for (float v : relevantScore) {
            maxRelevant = Math.max(maxRelevant, v);
            minRelevant = Math.min(minRelevant, v);
        }
        //System.out.println(maxRelevant + "   " + minRelevant);

        for(int i = 0; i < ratings.length; ++i) {
            if(movieRelevantScore.containsKey(ratings[i].movieId)) {
                float oldScore = movieRelevantScore.get(ratings[i].movieId);
                movieRelevantScore.replace(ratings[i].movieId, oldScore + relevantScore[i]/minRelevant);
            } else {
                movieRelevantScore.put(ratings[i].movieId, relevantScore[i]/minRelevant);
            }
        }
        for(int i = 0; i < movies.length; ++i) {
            if(!movieRelevantScore.containsKey(movies[i].ID)) {
                movieRelevantScore.put(movies[i].ID, (float) 0);
            }
        }
        Arrays.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                float scoreO1 = movieRelevantScore.get(o1.ID);
                float scoreO2 = movieRelevantScore.get(o2.ID);
                return Float.compare(scoreO2, scoreO1);
            }
        });

        float diff = 0;
        int separationPoint = 0;
        for(int i = 10; i < movies.length; ++i) {
            float thisDiff = movieRelevantScore.get(movies[i-1].ID) - movieRelevantScore.get(movies[i].ID);
            if(diff < thisDiff) {
                separationPoint = i;
                diff = thisDiff;
            }
        }
        //System.out.println(diff + "   " + separationPoint);
        separationPoint = Math.max(separationPoint, (int)movies.length/10);
        Movie[] res = new Movie[separationPoint];
        for(int i = 0; i < separationPoint; i ++) {
            res[i] = new Movie(movies[i]);
        }
        return res;
    }

    public void solve() throws IOException {
        if(this.badArgs) {
            return;
        }
        Movie[] specialList = this.find_relevant_movies().clone();
        //for(Movie i: specialList) System.out.println(i.title + " " + i.ID);
        this.calculateAvgScore();
        Arrays.sort(specialList, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                float scoreO1 = movieAvgScore.get(o1.ID);
                float scoreO2 = movieAvgScore.get(o2.ID);
                return Float.compare(scoreO2, scoreO1);
            }
        });
        for(int i = 0; i < 10; ++i)
        {
            System.out.format("%d. %s (%s)\n", i+1, specialList[i].title,
                    "http://www.imdb.com/title/tt" + movieImdbID.get(specialList[i].ID) + "/");
        }
    }
}
