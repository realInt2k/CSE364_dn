package com.help;

public class ageGapScore extends RelevanceScore{
    private float ageScorePercent;
    public ageGapScore(Float ageScorePercent) {
        this.ageScorePercent = ageScorePercent == null ? 90 : ageScorePercent;
    }
    @Override
    public float getScorePercent() {
        return this.ageScorePercent;
    }
}
