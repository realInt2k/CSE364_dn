package com.data;
/*UserID::MovieID::Rating::Timestamp
  i.e: 3796::356::5::966015052
 */
public class Rating {
    int UserId, MovieId, Rating, Timestamp;
    public Rating() {

    }
    public void input(String line) {
        String[] subLine = line.split("::");
        this.UserId = Integer.parseInt(subLine[0]);
        this.MovieId = Integer.parseInt(subLine[1]);
        this.Rating = Integer.parseInt(subLine[2]);
        this.Timestamp = Integer.parseInt(subLine[3]);
    }
}
