package com.help;

import org.junit.Assert;
import org.junit.Test;

public class userDirTest {
    @Test
    public void testCorrectDir ()
    {
        UserDir ud = new UserDir();
        Assert.assertEquals(System.getProperty("user.dir"), ud.get());
    }
}
