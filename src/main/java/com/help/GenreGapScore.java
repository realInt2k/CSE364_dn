package com.help;

public class GenreGapScore extends RelevanceScore{
    private float genreScorePercent;
    public GenreGapScore(Float genreScorePercent) {
        this.genreScorePercent = genreScorePercent == null ? 150 : Float.valueOf(genreScorePercent);
    }
    @Override
    public float getScorePercent() {
        return this.genreScorePercent;
    }

    @Override
    public void setScorePercent(float x) {
        this.genreScorePercent = x;
    }

    public static float getPercentCompare(String[] genreA, String[] genreB)
    {
        float cnt = 0;
        for (String i: genreA)
        {
            boolean same = false;
            for (String j: genreB)
            {
                if(i.equalsIgnoreCase(j))
                {
                    same = true;
                    break;
                }
            }
            if(same) {
                cnt += 1;
            }
        }
        return (float) (cnt/ (1.0*genreA.length));
    }
}
