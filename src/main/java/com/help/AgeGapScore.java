package com.help;

public class AgeGapScore extends RelevanceScore{
    private float ageScorePercent;
    public AgeGapScore(Float ageScorePercent) {
        this.ageScorePercent = ageScorePercent == null ? defaultScore.ageScore : Float.valueOf(ageScorePercent);
    }
    @Override
    public float getScorePercent() {
        return this.ageScorePercent;
    }

    @Override
    public void setScorePercent(float x) {
        this.ageScorePercent = x;
    }

    public static float getPercentCompare(Integer A, Integer B) {
        if(A == null || B == null) {
            return 1;
        } else {
            return (float) Math.pow(1.09, -Math.abs(A - B));
        }
    }
}
