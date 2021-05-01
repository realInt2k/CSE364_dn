package com.data;

public class Link {
    public int movieID;
    public String imdbID;
    public Link() {
        movieID = -1;
        imdbID = null;
    }
    public Link(Link another) {
        this.movieID = another.movieID;
        this.imdbID = another.imdbID;
    }
    public void input (String line) {
        String[] subLine = line.split("::");
        this.movieID = Integer.parseInt(subLine[0]);
        this.imdbID = subLine[1];
    }
}
