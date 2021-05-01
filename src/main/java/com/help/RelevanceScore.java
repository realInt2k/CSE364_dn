package com.help;

import com.data.Person;

public abstract class RelevanceScore {
    abstract public float getScorePercent();
    abstract public void setScorePercent(float x);

    public static float getScore(Person A, Person B) {
        AgeGapScore age = new AgeGapScore(null);
        float agePercent = age.getScorePercent();
        GenderGapScore gender = new GenderGapScore(null);
        float genderPercent = gender.getScorePercent();
        OccupationGapScore occupation = new OccupationGapScore(null);
        float occupationPercent = occupation.getScorePercent();
        GenreGapScore genre = new GenreGapScore(null);
        float genrePercent = genre.getScorePercent();
        float score = 0;

        score += AgeGapScore.getPercentCompare(A.getAge(), B.getAge()) * agePercent;
        score += GenderGapScore.getPercentCompare(A.getGender(), B.getGender()) * genderPercent;
        score += OccupationGapScore.getPercentCompare(A.getOccupation(), B.getOccupation()) * occupationPercent;
        if(A.genre != null && B.genre != null) {
            score += GenreGapScore.getPercentCompare(A.genre, B.genre) * genrePercent;
        }
        return score;
    }
    /*
    private float agePercent, genderPercent, occupationPercent, genrePercent;
    public RelevanceScore(){
        this.agePercent = 90;
        this.genderPercent = 80;
        this.occupationPercent = 50;
        this.genrePercent = 100;
    }
    public RelevanceScore(Int agePercent, Int genderPercent, Int occupationPercent, Int genrePercent){
        this.agePercent = agePercent == null? 90 : agePercent.intvalue();
        this.agePercent = genderPercent == null? 80 : genderPercent.intvalue();
        this.occupationPercent = occupationPercent == null ? 50 : occupationPercent.intvalue();
        this.genrePercent = genrePercent == null ? 100 : genrePercent.intvalue();
    }
    public float getScorePercent(String arg) {
        if(arg.equalsIgnoreCase("age")) {
            return this.agePercent;
        } else if(arg.equalsIgnoreCase("gender")) {
            return this.genderPercent;
        } else if(arg.equalsIgnoreCase("occupation")) {
            return this.occupationPercent;
        } else if(arg.equalsIgnoreCase("genre")) {
            return this.genrePercent;
        } else {
            return -1;
        }
    }
    public boolean setScorePercent(String arg, float percent) {
        if(arg.equalsIgnoreCase("age")) {
            this.agePercent = percent;
        } else if(arg.equalsIgnoreCase("gender")) {
            this.genderPercent = percent;
        } else if(arg.equalsIgnoreCase("occupation")) {
            this.occupationPercent = percent;
        } else if(arg.equalsIgnoreCase("genre")) {
            this.genrePercent = percent;
        } else {
            return false;
        }
        return true;
    }
    public int getScore(Reviewer A, Reviewer B) {
        return -1;
    }*/
}
