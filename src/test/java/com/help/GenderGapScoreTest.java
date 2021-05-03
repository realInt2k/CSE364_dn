package com.help;

import com.data.Person;
import junit.framework.TestCase;

public class GenderGapScoreTest extends TestCase {

    public void testSetScorePercent() {
        GenderGapScore gap = new GenderGapScore((float)1.2);
        gap.setScorePercent((float)1.3);
        assertEquals(gap.getScorePercent(), (float)1.3);
    }

    public void testGetPercentCompare() {
        assertEquals(GenderGapScore.getPercentCompare(null, null), (float)1);
        assertEquals(GenderGapScore.getPercentCompare(null, 'F'), (float)1);
        assertEquals(GenderGapScore.getPercentCompare('F', null), (float)1);
        //assertEquals(GenderGapScore.getPercentCompare('F', 'F'), (float)1);
    }
}