package com.help;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class OccupationGapScoreTest extends TestCase{
    @Test
    public void testOccupationGapScore(){
        OccupationGapScore blubla = new OccupationGapScore((float) defaultScore.occupationScore);
        Assert.assertEquals(defaultScore.occupationScore , blubla.getScorePercent(), 0.02);
    }
    @Test
    public void testGetPercentCompare() {
        float result1 = OccupationGapScore.getPercentCompare(Integer.valueOf(5), null);
        Assert.assertEquals(1.0, result1, 0.2);
        float result2 = OccupationGapScore.getPercentCompare(null, null);
        Assert.assertEquals(1, result2, 0.2);
    }
}
