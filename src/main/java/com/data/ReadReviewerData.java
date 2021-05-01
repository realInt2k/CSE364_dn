package com.data;

import com.help.UserDir;
import com.unist.FileReaderBuffer;

import java.io.IOException;

public abstract class ReadReviewerData {
    public static Reviewer[] data(String path) throws IOException {
        FileReaderBuffer reader = new FileReaderBuffer();
        UserDir ud = new UserDir();
        String[] lines = reader.readFile(ud.get() + path).clone();
        Reviewer[] reviewers = new Reviewer[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            reviewers[i] = new Reviewer();
            reviewers[i].input(lines[i]);
        }
        return reviewers;
    }
}
