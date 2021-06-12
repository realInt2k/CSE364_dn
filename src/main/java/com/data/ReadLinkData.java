package com.data;

import com.help.UserDir;
import com.unist.FileReaderBuffer;

import java.io.IOException;

public class ReadLinkData {
    public static Link[] data(String path) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        String[] lines = reader.readFile(UserDir.get() + path).clone();
        Link[] links = new Link[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            links[i] = new Link();
            links[i].input(lines[i]);
        }
        return links;
    }
}
