package com.help;

import org.junit.Assert;
import org.junit.Test;

public class userDirRemoved {
    @Test
    public void testCorrectDir ()
    {
        Assert.assertEquals(System.getProperty("user.dir"), UserDir.get());
    }
}
