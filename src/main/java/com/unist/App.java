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
        // args = new String[]{"Comedy", "educator"};
        // args = new String[]{"educator", "Adventure"};
        System.out.println();
        if(args.length <= 0) {
            System.out.println("Project compiled successfully, please input 2 args (genres occupation) to run");
        }
        else {
            if(args.length > 2) {
                System.out.println("More than 2 arguments inputted, program will only" +
                        "consider the first two");
            }
            Milestone1 solver = new Milestone1();
            solver.initialize();

            if (solver.isWrongOrder(args)) {
                System.out.println("You should input genre before occupation");
                return;
            }
            
            solver.solve(args);
            double output = solver.output();
            if (output != -1) {
                // No error
                System.out.println("Average score = " + solver.output());
            }
        }
        System.out.println();
    }
}
