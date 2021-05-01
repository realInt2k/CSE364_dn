package com.data;

import com.help.GenreGapScore;
import com.help.UserDir;
import com.unist.FileReaderBuffer;

import java.io.IOException;
import java.util.ArrayList;

public abstract class ReadRatingData {
    public static Rating[] data(String path) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        UserDir ud = new UserDir();
        String[] lines = reader.readFile(ud.get() + path).clone();
        Rating[] ratings = new Rating[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            ratings[i] = new Rating();
            ratings[i].input(lines[i]);
        }
        return ratings;
    }
}

