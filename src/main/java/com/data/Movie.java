package com.data;
/*MovieID::Title::Genres
 i.e: 1::Toy Story (1995)::Animation|Children's|Comedy
 */
public class Movie {
    public String[] cat; //short for categories, representing Genres, staying closed to problem's statement.
    public String title;
    public int ID;
    public String genre() {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < this.cat.length; ++i) {
            if(i == 0) {
                res.append(this.cat[i]);
            } else {
                res.append("|").append(this.cat[i]);
            }
        }
        return res.toString();
    }
    public Movie() {
        cat = null;
        title = null;
        ID = -1;
    }
    public Movie(Movie another) {
        this.cat = another.cat.clone();
        this.title = another.title;
        this.ID = another.ID;
    }
    public void input (String line) {
        String[] subLine = line.split("::");
        this.ID = Integer.parseInt(subLine[0]);
        this.title = subLine[1];
        this.cat = subLine[2].split("\\|").clone();
    }
}
