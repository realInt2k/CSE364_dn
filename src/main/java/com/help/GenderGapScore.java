package com.help;

public class GenderGapScore extends RelevanceScore{
    private float genderScorePercent;
    public GenderGapScore(Float genderScorePercent) {
        this.genderScorePercent = genderScorePercent == null ? defaultScore.genderScore : Float.valueOf(genderScorePercent);
    }
    @Override
    public float getScorePercent() {
        return this.genderScorePercent;
    }

    @Override
    public void setScorePercent(float x) {
        this.genderScorePercent = x;
    }

    public static float getPercentCompare(Character A, Character B) {
        if(A == null || B == null) {
            return (float)1.0;
        } else {
            return A == B ? (float) 1.0 : (float) 0.5;
        }
    }
}
