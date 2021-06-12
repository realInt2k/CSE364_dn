package com.unist.webApp;

import com.data.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RatingDAL {
    List<Rating> getAllRating();
}
