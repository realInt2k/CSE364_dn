package com.unist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class fileReader {
    /* Make a scanner (using File), scan each line, add it to ArrayList<String>
     * then literally convert it to lines.
     * */
    public String[] readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scan = new Scanner(file);
        List<String> tmp = new ArrayList<String>();
        int i = 0;
        while(scan.hasNext())
        {
            String line = scan.nextLine();
            tmp.add(line);
        }
        String[] lines = new String[tmp.size()];
        tmp.toArray(lines);
        return lines;
    }
}
