package com.data;
/*MovieID::Title::Genres
 i.e: 1::Toy Story (1995)::Animation|Children's|Comedy
 */
public class Movie {
    public String[] cat; //short for categories, representing Genres, staying closed to problem's statement.
    public String title;
    public int ID;
    public Movie() {
        cat = null;
        title = null;
        ID = -1;
    }
    public void input (String line) {
        String[] subLine = line.split("::");
        this.ID = Integer.parseInt(subLine[0]);
        this.title = subLine[1];
        if(subLine[2].indexOf('|') != -1)
            this.cat = subLine[2].split("|").clone();
        else {
            this.cat = new String[1];
            this.cat[0] = subLine[2];
        }
    }
}
