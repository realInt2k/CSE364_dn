package com.unist.webBackend;

// "/users/recommendations"
public class argument1 {
    public String gender = null, age = null, occupation = null;
    public String genre = null;
    public String genres = null;

    public String[] args() {
        String[] res = new String[4];
        res[0] = this.gender;
        res[1] = this.age;
        res[2] = this.occupation;
        if (this.genres == null)
            res[3] = this.genre;
        else
            res[3] = this.genres;
        return res;
    }
}
