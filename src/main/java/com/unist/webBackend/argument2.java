package com.unist.webBackend;

/*http://localhost:8080/movies/recommendations*/
public class argument2 {
    public String title = null;
    public String limit = null;

    public String[] args() {
        String[] res = new String[2];
        res[0] = this.title;
        res[1] = this.limit;
        return res;
    }
}
