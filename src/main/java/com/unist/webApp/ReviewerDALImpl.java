package com.unist.webApp;

import com.data.Reviewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ReviewerDALImpl implements ReviewerDAL {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Reviewer> getAllReviewer() {
        return mongoTemplate.findAll(Reviewer.class);
    }
}
