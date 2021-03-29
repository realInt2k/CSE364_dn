package com.unist;
import com.unist.fileReader;
import com.help.userDir;

import java.io.FileNotFoundException;

public class app {
    public static void main (String[] args) throws FileNotFoundException {
        fileReader reader = new fileReader();
        userDir ud = new userDir();
        String[] lines = reader.readFile(ud.get()+"/data/users.dat");
        System.out.println("number of lines in users.dat is : " + lines.length);
    }
}
