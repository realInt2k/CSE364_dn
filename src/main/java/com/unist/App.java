package com.unist;
import com.data.Reviewer;
import com.help.UserDir;

import java.io.FileNotFoundException;

public class App {
    public Reviewer[] reviewers;
    public void initialize() throws FileNotFoundException {
        FileReader reader = new FileReader();
        UserDir ud = new UserDir();
        String[] lines = reader.readFile(ud.get()+"/data/users.dat");
        reviewers = new Reviewer[lines.length];
        for(int i = 0; i < lines.length; ++i) {
            reviewers[i].input(lines[i]);
        }
    }
    public static void main (String[] args) {
    }
}
