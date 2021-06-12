package com.help;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class AgeGapScoreRemoved extends TestCase {
    @Test
    public void testSetScorePercent() {
        AgeGapScore gap = new AgeGapScore((float)1.2);
        gap.setScorePercent((float)1.3);
        assertEquals(gap.getScorePercent(), (float)1.3);
    }
    @Test
    public void testGetPercentCompare() {
        Assert.assertEquals(Float.compare(AgeGapScore.getPercentCompare(null, 12), (float)1), 0);
        Assert.assertEquals(Float.compare(AgeGapScore.getPercentCompare(12, null), (float)1), 0);
        Assert.assertEquals(Float.compare(AgeGapScore.getPercentCompare(null, null), (float)1), 0);
    }
}