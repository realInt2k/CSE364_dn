package com.unist;
import com.data.Universal;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;

@SpringBootApplication
//@EnableMongoRepositories
public class App extends SpringBootServletInitializer {

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

    public static void Mile2(String[] args) throws IOException, JSONException {
        System.out.println();
        Milestone2 solver = new Milestone2(args);
        solver.solve();
        System.out.println();
    }

    public static void main (String[] args) throws IOException {
        Universal.prepareData();
        Universal.countMovie();
        Universal.calculateAvgScore();
        // test;
        SpringApplication.run(App.class, args);
    }
}
