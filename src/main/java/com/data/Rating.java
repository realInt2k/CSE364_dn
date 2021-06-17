package com.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*UserID::MovieID::Rating::Timestamp
  i.e: 3796::356::5::966015052
 */
@Document
public class Rating {
    //@Id
    public int userId;
    public int movieId;
    public int rating;
    public int timestamp;
    public Rating() {

    }
    public void input(String line) {
        String[] subLine = line.split("::");
        this.userId = Integer.parseInt(subLine[0]);
        this.movieId = Integer.parseInt(subLine[1]);
        this.rating = Integer.parseInt(subLine[2]);
        this.timestamp = Integer.parseInt(subLine[3]);
    }
}
