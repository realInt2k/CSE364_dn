package com.unist.webApp;

import com.data.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class RatingDALImpl implements RatingDAL {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Rating> getAllRating() {
        return mongoTemplate.findAll(Rating.class);
    }
}
