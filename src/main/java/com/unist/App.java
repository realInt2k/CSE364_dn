package com.unist;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
@RestController
public class App {

    public static void Mile1(String[] args) throws IOException {
        System.out.println();
        if(args.length <= 0) {
            System.out.println("Project compiled successfully, please input 2 args (genres occupation) to run");
        }
        else {
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

    public static void Mile2(String[] args) throws IOException {
        System.out.println();
        Milestone2 solver = new Milestone2(args);
        solver.solve();
        System.out.println();
    }

    public static void main (String[] args) throws IOException {
        SpringApplication.run(App.class, args);
        /*if(args.length <= 2) {
            Mile1(args);
        } else {
            Mile2(args);
        }*/
    }
}
