package com.unist;
import com.unist.fileReader;
import com.help.userDir;

import java.io.FileNotFoundException;

public class app {
    public static void main (String[] args) throws FileNotFoundException {
        System.out.println("test");
        fileReader fr = new fileReader();
        userDir ud = new userDir();
        ud.print();
        //fr.openFile("../../../../data/movies.dat");
    }
}
