package com.unist;

import com.data.*;
import com.help.GenreGapScore;
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
    public float maxRelevant = 0;
    public float maxScore = 0;

    public boolean badArgs = false;
    public Map<Integer, String> movieImdbID = new HashMap<>();
    public Map<Integer, Float> movieRelevantScore = new HashMap<>();
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

    public boolean genre_check(String [] categories, boolean v) { // v as verbose
        if(categories == null || categories.length == 0)
            return false;
        for (int i = 0; i < categories.length; i++) {
            for (int j = 0; j < genres.length; j++) {
                if (categories[i].equalsIgnoreCase(genres[j])) {
                    j = genres.length + 2;
                }
                if (j == genres.length - 1) {
                    if(v == true) {
                        System.out.format("No such genre: \"%s\"%n", categories[i]);
                        System.out.format("Try one of these: %n");
                    }
                    print_genres();
                    return false;
                }
            }
        }
        return true;
    }

    public Milestone2(String[] args) throws IOException {
        this.prepareData();
        if(args.length > 4) {
            System.out.println("arguments length > 4, please follow this format: " +
                    "\"Gender\" " +
                    "\"Age\" " +
                    "\"Occupation\"");
            this.badArgs = true;
            return;
        }

        StringBuilder welcome = new StringBuilder();
        welcome.append("Finding movies for a customer with: \n\n");
        if(!args[0].isEmpty()) {
            if(args[0].toLowerCase().charAt(0) != 'f' && args[0].toLowerCase().charAt(0) != 'm') {
                this.badArgs = true;
                this.badArgsExit("Gender \"" + args[0] + "\" is not \"F\" or \"M\"");
                return;
            }
            customer.setGender(args[0].charAt(0));
            welcome.append("\tgender: ").append(customer.getGender()).append('\n');
        } else {
           welcome.append("\tgender: no preference \n");
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
            welcome.append("\tage: ").append(customer.getAge()).append('\n');
        } else {
            welcome.append("\tage: no preference\n");
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
            welcome.append("\toccupation: ").append(args[2]).append('\n');
        } else {
            welcome.append("\toccupation: no preference \n");
        }
        System.out.println();
        if(occupationNotFound)
            welcome.append("No such occupation found, use 'other' as default\n");
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
            if(!genre_check(genre, true)) {
                this.badArgs = true;
                return;
            }
            welcome.append("\tGenres: ");
            for(String i : genre) welcome.append(i).append(" ");
            welcome.append('\n');
        }
        System.out.println(welcome);
    }

    // dependent on Rating && Movie list
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
        }
    }

    // dependent on Rating && Movie list
    public void calculateAvgScore() {
        for(Rating rate:ratings) {
            int cnt = movieCnt.get(rate.movieId);
            if(cnt == 0) {
                movieAvgScore.put(rate.movieId, (float)0);
                continue;
            }
            if(!movieAvgScore.containsKey(rate.movieId)) {
                movieAvgScore.put(rate.movieId, (float)rate.rating/(float)cnt);
            } else {
                float sum = movieAvgScore.get(rate.movieId);
                float newSum = (sum + (float)rate.rating/(float)cnt);
                movieAvgScore.replace(rate.movieId, newSum);
            }
        }
        for(Movie movie:movies) {
            if(!movieAvgScore.containsKey(movie.ID)) {
                movieAvgScore.put(movie.ID, (float)0);
            } else {
                float score = movieAvgScore.get(movie.ID);
                maxScore = Math.max(maxScore, score);
            }
        }

    }

    public float[] analyseRating(Person A) {
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
            maxRelevant = Math.max(maxRelevant, score[i]);
        }
        return score;
    }

    public Movie [] find_relevant_movies() {
        float[] relevantScore = this.analyseRating(customer).clone();

        for(int i = 0; i < ratings.length; ++i) {
            float cnt = (float)movieCnt.get(ratings[i].movieId);
            if(cnt == 0)
            {
                movieRelevantScore.put(ratings[i].movieId, (float)0);
                continue;
            }
            if(movieRelevantScore.containsKey(ratings[i].movieId)) {
                float oldScore = movieRelevantScore.get(ratings[i].movieId);
                movieRelevantScore.replace(ratings[i].movieId, oldScore + (relevantScore[i]/cnt));
            } else {
                movieRelevantScore.put(ratings[i].movieId, (relevantScore[i]/cnt));
            }
        }

        for(Movie x : movies) {
            if(!movieRelevantScore.containsKey(x.ID))
                movieRelevantScore.put(x.ID, (float)0);
            maxRelevant = Math.max(maxRelevant, movieRelevantScore.get(x.ID));
        }

        Arrays.sort(movies, (o1, o2) -> {
            int cntO1 = movieCnt.get(o1.ID);
            int cntO2 = movieCnt.get(o2.ID);
            if ((cntO1 >= nReviewsThreshold && cntO2 >= nReviewsThreshold) ||
                    (cntO1 < nReviewsThreshold && cntO2 < nReviewsThreshold)) {
                float scoreO1 = movieRelevantScore.get(o1.ID);
                float scoreO2 = movieRelevantScore.get(o2.ID);
                return Float.compare(scoreO2, scoreO1);
            } else {
                return (cntO2 - cntO1);
            }
        });

        // select data
        float diff = 0;
        int separationPoint = 0;
        for(int i = 10; i < movies.length; ++i) {
            float thisDiff = movieRelevantScore.get(movies[i-1].ID) - movieRelevantScore.get(movies[i].ID);
            if(diff < thisDiff) {
                separationPoint = i;
                diff = thisDiff;
            }
        }
        //System.out.println(diff + "   " + separationPoint); // DEBUG
        separationPoint = Math.max(separationPoint, movies.length / 100 * 5); //5%

        Movie[] res = new Movie[separationPoint];
        for(int i = 0; i < separationPoint; i ++) {
            //System.out.println(movies[i].title + " " + movieRelevantScore.get(movies[i].ID));
            res[i] = new Movie(movies[i]);
        }
        return res;
    }

    public void filterData(Person person) {
        ArrayList<Rating> newRatings = new ArrayList<>();
        ArrayList<Movie> newMovies = new ArrayList<>();
        ArrayList<Reviewer> newReviewers = new ArrayList<>();
        for(Movie x : movies) {
            if(GenreGapScore.getPercentCompare(x.cat, person.genre) == 1)
                newMovies.add(x);
        }
        if(newMovies.size() < 10)
        {
            for(Movie x : movies) {
                if (GenreGapScore.getPercentCompare(x.cat, person.genre) != 1)
                    newMovies.add(x);
                if(newMovies.size() >= 10)
                    break;
            }
        }
        HashMap<Integer, Boolean> selectReviewers = new HashMap<>();
        for(Reviewer x: reviewers) selectReviewers.put(x.ID, false);
        for(Rating x : ratings) {
            if(GenreGapScore.getPercentCompare(movieMap.get(x.movieId).cat, person.genre) == 1) {
                if(selectReviewers.containsKey(x.userId))
                    selectReviewers.replace(x.userId, true);
                else
                    selectReviewers.put(x.userId, true);
                newRatings.add(x);
            }
        }
        for(Reviewer x: reviewers)
            if(selectReviewers.get(x.ID)) {
                newReviewers.add(x);
            }
        this.reviewers = new Reviewer[newReviewers.size()];
        newReviewers.toArray(this.reviewers);
        this.ratings = new Rating[newRatings.size()];
        newRatings.toArray(this.ratings);
        this.movies = new Movie[newMovies.size()];
        newMovies.toArray(this.movies);

        this.countMovie();
        this.calculateAvgScore();

        /*  https://www.statisticshowto.com/how-to-use-slovins-formula/*/
        float errorPercent = (float)0.10;
        nReviewsThreshold = (int)((float)reviewers.length / ((float)1 + (float)reviewers.length * Math.pow(errorPercent, (float)2)));
        //System.out.println("Threshold:  over " + nReviewsThreshold + " reviews for " + errorPercent*100 + "% error (kinda)");
    }

    public void solve() throws IOException {
        if(this.badArgs) {return;}
        this.filterData(customer);
        Movie[] specialList = this.find_relevant_movies().clone();
        Arrays.sort(specialList, (o1, o2) -> {
            int cntO1 = movieCnt.get(o1.ID);
            int cntO2 = movieCnt.get(o2.ID);
            if((cntO1 >= nReviewsThreshold && cntO2 >= nReviewsThreshold) ||
                    (cntO1 < nReviewsThreshold && cntO2 < nReviewsThreshold)){
                float scoreO1 = movieAvgScore.get(o1.ID);
                float scoreO2 = movieAvgScore.get(o2.ID);
                float relScoreO1 = movieRelevantScore.get(o1.ID);
                float relScoreO2 = movieRelevantScore.get(o2.ID);
                return Float.compare(scoreO2/maxScore + relScoreO2/maxRelevant, scoreO1/maxScore + relScoreO1/maxRelevant);
            } else {
                return cntO2 - cntO1;
            }
        });
        for(int i = 0; i < 10; ++i)
        {
            /* THIS IS DEBUGING LINEs */
//            System.out.format("%d. %s (%s) avg score:%.3f, n.o ratings::%d, relScore:%.3f \n",
//                    i+1, specialList[i].title,
//                    "imdb " + movieImdbID.get(specialList[i].ID) + "/",
//                    movieAvgScore.get(specialList[i].ID),
//                    movieCnt.get(specialList[i].ID),
//                    movieRelevantScore.get(specialList[i].ID));
            /*THIS IS THE CORRECT OUTPUT LINES*/
            System.out.format("%d. %s (%s)\n",
                    i+1, specialList[i].title,
                    "http://www.imdb.com/title/tt" + movieImdbID.get(specialList[i].ID) + "/");
        }
        //this.genre_check(new String[]{"children's", "war", "Documentary", "war", "Fantasy"}, true);
    }
}
