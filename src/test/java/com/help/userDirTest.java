package com.help;

import org.junit.Assert;
import org.junit.Test;

public class userDirTest {
    @Test
    public void testCorrectDir ()
    {
        userDir ud = new userDir();
        Assert.assertEquals(System.getProperty("user.dir"), ud.get());
    }
}
