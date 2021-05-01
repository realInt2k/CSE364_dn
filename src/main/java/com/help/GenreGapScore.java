package com.help;

public class GenreGapScore extends RelevanceScore{
    private float genreScorePercent;
    public GenreGapScore(Float genreScorePercent) {
        this.genreScorePercent = genreScorePercent == null ? defaultScore.genreScore : Float.valueOf(genreScorePercent);
    }
    @Override
    public float getScorePercent() {
        return this.genreScorePercent;
    }

    @Override
    public void setScorePercent(float x) {
        this.genreScorePercent = x;
    }

    /*
    Determine how many genres in A match with B.
     */
    public static float getPercentCompare(String[] genreA, String[] genreB)
    {
        if(genreA == null || genreB == null)
            return 1;
        float cntA = 0;
        float allA = genreA.length, allB = genreB.length;
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
                cntA += 1;
                break;
            }
        }
        if(cntA > 0)
            return 1;
        else
            return 0;
    }
}
