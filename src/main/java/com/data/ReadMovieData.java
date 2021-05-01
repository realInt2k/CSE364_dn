package com.data;

import com.help.UserDir;
import com.unist.FileReaderBuffer;

import java.io.IOException;

public abstract class ReadMovieData {
    public static Movie[] data(String path) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        UserDir ud = new UserDir();
        String[] lines = reader.readFile(ud.get() + path).clone();
        Movie[] movies = new Movie[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            movies[i] = new Movie();
            movies[i].input(lines[i]);
        }
        return movies;
    }
}
