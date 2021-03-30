package com.unist;
import com.data.*;
import com.help.UserDir;
import java.io.FileNotFoundException;

public class App {

    public static void main (String[] args) throws FileNotFoundException {
        Milestone1 solver = new Milestone1();
        solver.initialize();
        solver.solve(args);
        System.out.println("average score = " + solver.output());
    }
}
