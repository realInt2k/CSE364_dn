package com.help;

public class OccupationGapScore extends RelevanceScore{
    private float OccupationGapScore;
    OccupationGapScore(Float score) {
        this.OccupationGapScore= score == null ? 75: Float.valueOf(score);
    }
    @Override
    public float getScorePercent() {
        return this.OccupationGapScore;
    }

    @Override
    public void setScorePercent(float x) {
        this.OccupationGapScore=x;
    }

    public static float getPercentCompare (int A, int B) {
        return A == B ? (float) 1.0 : (float) 0.5;
    }
}
