package com.unist.webApp;

import com.data.Reviewer;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewerDAL {
    List<Reviewer> getAllReviewer();
}
