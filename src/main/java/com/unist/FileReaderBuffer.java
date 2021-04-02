package com.unist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderBuffer {
    public String[] readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<String> tmp = new ArrayList<String>();
        String line = reader.readLine();
        while(line != null) {
            tmp.add(line);
            line = reader.readLine();
        }
        reader.close();
        String[] lines = new String[tmp.size()];
        tmp.toArray(lines);
        return lines;
    }
}
