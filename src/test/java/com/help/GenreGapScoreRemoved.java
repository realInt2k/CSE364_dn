package com.help;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class GenreGapScoreRemoved extends TestCase {
    @Test
    public void testSetScorePercent() {
        GenreGapScore gap = new GenreGapScore((float)1.2);
        gap.setScorePercent((float)1.3);
        assertEquals(gap.getScorePercent(), (float)1.3);
    }
    @Test
    public void testGetPercentCompare() {
        Assert.assertEquals(Float.compare(GenreGapScore.getPercentCompare(null, new String[] {"A"}), (float)1), 0);
        Assert.assertEquals(Float.compare(GenreGapScore.getPercentCompare(new String[] {"A"}, null), (float)1), 0);
        Assert.assertEquals(Float.compare(GenreGapScore.getPercentCompare(null, null), (float)1), 0);
    }
}