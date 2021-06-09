package com.unist.webApp;
import com.data.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface MovieDAL {
    List<Movie> getAllMovies();

    Movie getMovieById(int movieId);

    Movie addNewMovie(Movie m);

    Object getAllMovies(int movieId);

    String getMovieName(int movieId);

}
