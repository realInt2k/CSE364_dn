package com.help;

import com.data.Person;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RelevanceScoreTest extends TestCase {
    @Test
    public void testGetScorePercent() {
        RelevanceScore gap = new GenderGapScore((float)1.2);
        gap.setScorePercent((float)1.3);
        assertEquals(gap.getScorePercent(), (float)1.3);
    }
    @Test
    public void testSetScorePercent() {
        assertEquals(GenderGapScore.getPercentCompare(null, null), (float)1);
    }
    @Test
    public void testGetScore() {
        RelevanceScore rel = new RelevanceScore() {
            @Override
            public float getScorePercent() {
                return 0;
            }

            @Override
            public void setScorePercent(float x) {

            }
        };
        Person A = new Person();
        A.setGenre(new String[]{"Interesting", "Alien"});
        Person B = new Person();
        B.setGenre(new String[]{"Adult", "Awesome"});
        Person C = new Person(null);
        float scAB = RelevanceScore.getScore(A, B);
        float scAC = RelevanceScore.getScore(A, C);
        float scCB = RelevanceScore.getScore(C, B);
        Assert.assertEquals(Float.compare(scAB, scAC), 0);
        Assert.assertEquals(Float.compare(scAB, scCB), 0);
    }
}
