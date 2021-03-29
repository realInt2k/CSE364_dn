package com.unist;
import com.unist.fileReader;
import com.help.userDir;

import java.io.FileNotFoundException;

public class app {
    public static void main (String[] args) throws FileNotFoundException {
        fileReader reader = new fileReader();
        userDir ud = new userDir();
        reader.openFile(ud.get()+"/data/users.dat");
    }
}
