package com.help;

public class GenderGapScore extends RelevanceScore{
    private float genderScorePercent;
    public GenderGapScore(Float genderScorePercent) {
        this.genderScorePercent = genderScorePercent == null ? 80 : Float.valueOf(genderScorePercent);
    }
    @Override
    public float getScorePercent() {
        return this.genderScorePercent;
    }

    @Override
    public void setScorePercent(float x) {
        this.genderScorePercent = x;
    }

    public static float getPercentCompare(char A, char Y) {
        return A==Y ? (float) 1.0 : (float) 0.5;
    }
}
