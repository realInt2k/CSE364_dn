package com.unist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fileReader {
    public int openFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scan = new Scanner(file);
        int i = 0;
        while(scan.hasNext())
        {
            String line = scan.nextLine();
            if(i < 3) {
                System.out.println(line);
            }
        }
        return 1;
    }
}
