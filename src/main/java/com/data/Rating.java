package com.data;
/*UserID::MovieID::Rating::Timestamp
  i.e: 3796::356::5::966015052
 */
public class Rating {
    public int userId, movieId, rating, timestamp;
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
