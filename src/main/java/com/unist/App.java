package com.unist;
import com.data.*;
import com.help.UserDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main (String[] args) throws IOException {
        // args = new String[]{"Adventure", "educator"};
        if(args.length <= 0) {
            System.out.println("project compiled successfully, please input 2 args (genres occupation) to run");
        } else {
            Milestone1 solver = new Milestone1();
            solver.initialize();
            solver.solve(args);
            double output = solver.output();
            if (output != -1) {
                // No error
                System.out.println("average score = " + solver.output());
            }
        }
    }
}
