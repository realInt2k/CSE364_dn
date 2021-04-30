package com.help;

public class relevance_score {
    private float age_percent, gender_percent, occupation_percent, genre_percent;
    public relevance_score(){
        this.age_percent = 50;
        this.gender_percent = 38;
        this.occupation_percent = 12;
    }
    public relevance_score(Int age_percent, Int gender_percent, Int occupation_percent, Int genre_percent){
        this.age_percent = age_percent == null? 50 : age_percent.intvalue();
        this.gender_percent = gender_percent == null? 38 : gender_percent.intvalue();
        this.occupation_percent = occupation_percent == null ? 12 : occupation_percent.intvalue();
        this.genre_percent = genre_percent == null ? 95 : genre_percent.intvalue();
    }
    public get_score(
            
    )
}
