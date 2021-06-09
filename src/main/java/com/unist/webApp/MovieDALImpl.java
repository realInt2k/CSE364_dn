package com.unist.webApp;

import com.data.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class MovieDALImpl implements MovieDAL {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Movie> getAllMovies() {
        return mongoTemplate.findAll(Movie.class);
    }

    @Override
    public Movie getMovieById(int movieId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("movieId").is(movieId));
        return mongoTemplate.findOne(query, Movie.class);
    }

    @Override
    public Movie addNewMovie(Movie m) {
        mongoTemplate.insert(m);
        return m;
    }

    @Override
    public Object getAllMovies(int movieId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("movieID").is(movieId));
        Movie m = mongoTemplate.findOne(query, Movie.class);
        return m != null?m:"movie not found";
    }

    @Override
    public String getMovieName(int movieId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("movieId").is(movieId));
        Movie m = mongoTemplate.findOne(query, Movie.class);
        return m.title;
    }

}
