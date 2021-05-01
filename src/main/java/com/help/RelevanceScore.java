package com.help;

import com.data.Person;

public abstract class RelevanceScore extends defaultScore {
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
}
