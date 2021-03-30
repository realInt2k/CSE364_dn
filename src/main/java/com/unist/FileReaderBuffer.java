package com.unist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderBuffer {
    public String[] readFile(String path) throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(path));
        String line = reader.readLine();
        while(line != null) {
            System.out.println(line);
            line = reader.readLine();
        }
        reader.close();
    }
}
