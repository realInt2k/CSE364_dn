package com.unist.webApp;

import com.data.Reviewer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewerRepository extends MongoRepository<Reviewer, String> {
}
