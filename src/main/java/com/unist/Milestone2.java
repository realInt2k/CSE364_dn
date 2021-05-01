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
    public int nReviewsThreshold = 0;

    public boolean badArgs = false;
    public Map<Integer, String> movieImdbID = new HashMap<>();
    public Map<Integer, Float> movieRelevantScore = new HashMap<>();
    public Map<Integer, Float> movieRelevantStd = new HashMap<>();
    public Map<Integer, Float> movieAvgScoreStd = new HashMap<>();
    public Map<Integer, Float> movieAvgScore = new HashMap<>();
    public Map<Integer, Integer> movieCnt = new HashMap<>();

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
    public void badArgsExit(String problem) {
        System.out.println(problem);
    }

    public void print_genres() {
        int i = 0;
        for(; i < genres.length; ++i) {
            System.out.format("- %s ", genres[i]);
            if(i%4 == 0) System.out.println();
        }
        if(i%4 != 0) System.out.println();
    }

    public boolean genre_check(String [] categories) {
        for (int i = 0; i < categories.length; i++) {
            for (int j = 0; j < genres.length; j++) {
                if (categories[i].equalsIgnoreCase(genres[j])) {
                    j = genres.length + 2;
                }
                if (j == genres.length - 1) {
                    System.out.format("No such genre: \"%s\"%n", categories[i]);
                    System.out.format("Try one of these: %n");
                    print_genres();
                    return false;
                }
            }
        }
        return true;
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
            if(args[3].isEmpty()) {
                this.badArgs = true;
                this.badArgsExit("4 arguments but no genres were inputted, please input some.");
                System.out.format("Try some of these: \n");
                print_genres();
                return;
            }
            genre = args[3].split("\\|");
            customer.setGenre(genre);
            if(!genre_check(genre)) {
                this.badArgs = true;
                return;
            }
            welcome += "\tGenres: ";
            for(String i : genre) {
                welcome += i  + " ";
            }
            welcome += '\n';
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
            /*if(movie.ID == 2019) {
                System.out.println(score[i]);
            }*/
        }
        return score;
    }

    public void countMovie() {
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
            nReviewsThreshold += movieCnt.get(movie.ID);
        }
        nReviewsThreshold /= movies.length;
        //System.out.println(nReviewsThreshold);
    }
    public void calculateAvgScore() {
        for(Rating rate:ratings) {
            int cnt = movieCnt.get(rate.movieId);
            if(!movieAvgScore.containsKey(rate.movieId)) {
                movieAvgScore.put(rate.movieId, (float)rate.rating/(float)cnt);
            } else {
                float sum = movieAvgScore.get(rate.movieId);
                float newSum = (sum + (float)rate.rating/(float)cnt);
                movieAvgScore.replace(rate.movieId, newSum);
            }
        }

        for(int i = 0; i < ratings.length; ++i) {
            float cnt = (float)movieCnt.get(ratings[i].movieId);
            float mean = movieAvgScore.get(ratings[i].movieId);

            if(movieAvgScoreStd.containsKey(ratings[i].movieId)) {
                float oldScore = movieAvgScoreStd.get(ratings[i].movieId);
                movieAvgScoreStd.replace(ratings[i].movieId, oldScore +
                        (ratings[i].rating - mean)*(ratings[i].rating - mean)/cnt);
            } else {
                movieAvgScoreStd.put(ratings[i].movieId,
                        (ratings[i].rating - mean) * (ratings[i].rating - mean)/cnt);
            }
        }

        for(Movie movie:movies) {
            if(!movieAvgScore.containsKey(movie.ID)) {
                movieAvgScore.put(movie.ID, (float)0);
                movieAvgScoreStd.put(movie.ID, (float)0);
            } else {
                float std = movieAvgScoreStd.get(movie.ID);
                float score = movieAvgScore.get(movie.ID);
                movieAvgScoreStd.replace(movie.ID, score - std);
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
            float cnt = (float)movieCnt.get(ratings[i].movieId);
            if(movieRelevantScore.containsKey(ratings[i].movieId)) {
                float oldScore = movieRelevantScore.get(ratings[i].movieId);
                movieRelevantScore.replace(ratings[i].movieId, oldScore + (relevantScore[i]/cnt));
            } else {
                movieRelevantScore.put(ratings[i].movieId, (relevantScore[i]/cnt));
            }
        }

        for(int i = 0; i < ratings.length; ++i) {
            float cnt = (float)movieCnt.get(ratings[i].movieId);
            float mean = movieRelevantScore.get(ratings[i].movieId);

            if(movieRelevantStd.containsKey(ratings[i].movieId)) {
                float oldScore = movieRelevantStd.get(ratings[i].movieId);
                movieRelevantStd.replace(ratings[i].movieId, oldScore +
                        (relevantScore[i] - mean)*(relevantScore[i] - mean)/cnt);
            } else {
                movieRelevantStd.put(ratings[i].movieId,
                        (relevantScore[i] - mean) * (relevantScore[i] - mean)/cnt);
            }
        }


        for(int i = 0; i < movies.length; ++i) {
            if(!movieRelevantScore.containsKey(movies[i].ID)) {
                movieRelevantStd.put(movies[i].ID, (float) 0);
                movieRelevantScore.put(movies[i].ID, (float) 0);
            } else {
                float variance = movieRelevantStd.get(movies[i].ID);
                float relScore = movieRelevantScore.get(movies[i].ID);
                movieRelevantStd.replace(movies[i].ID, (float) Math.sqrt(variance));
                movieRelevantScore.replace(movies[i].ID, relScore - (float)Math.sqrt(variance));
            }
        }
        Arrays.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                int cntO1 = movieCnt.get(o1.ID);
                int cntO2 = movieCnt.get(o2.ID);
                if(cntO1 >= nReviewsThreshold && cntO2 >= nReviewsThreshold) {
                    float scoreO1 = movieRelevantScore.get(o1.ID);
                    float scoreO2 = movieRelevantScore.get(o2.ID);
                    return Float.compare(scoreO2, scoreO1 );
                } else {
                    return (cntO2 - cntO1);
                }
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
        this.countMovie();
        Movie[] specialList = this.find_relevant_movies().clone();
        this.calculateAvgScore();
        Arrays.sort(specialList, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                int cntO1 = movieCnt.get(o1.ID);
                int cntO2 = movieCnt.get(o2.ID);
                if(cntO1 >= nReviewsThreshold && cntO2 >= nReviewsThreshold) {
                    float scoreO1 = movieAvgScore.get(o1.ID);
                    float scoreO2 = movieAvgScore.get(o2.ID);
                    float relScoreO1 = movieRelevantScore.get(o1.ID);
                    float relScoreO2 = movieRelevantScore.get(o2.ID);
                    return Float.compare(2*scoreO2 + (float)0.6*relScoreO2, 2*scoreO1 + (float)0.6*relScoreO1);
                } else {
                    return cntO2 - cntO1;
                }
            }
        });

        for(int i = 0; i < 10; ++i)
        {
            /* THIS IS DEBUGING LINE */
            System.out.format("%d. %s (%s) avg score:%.3f, n.o ratings::%d, relScore:%.3f \n",
                    i+1, specialList[i].title,
                    "http://www.imdb.com/title/tt" + movieImdbID.get(specialList[i].ID) + "/",
                    movieAvgScore.get(specialList[i].ID),
                    movieCnt.get(specialList[i].ID),
                    movieRelevantScore.get(specialList[i].ID));
        }
    }
}
