package com.unist;

import com.data.Movie;
import junit.framework.TestCase;
import org.junit.Assert;

import javax.sound.midi.MidiEvent;
import java.io.IOException;
import java.io.PrintStream;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;

public class Milestone2Test extends TestCase {

    public void testPrepareData() throws IOException {
        Milestone2 mile2 = new Milestone2(new String[]{"F", "25", "gradStudent"});
        mile2.prepareData();
        //movie1.input("1::Toy Story (1995)::Animation|Children's|Comedy");
        Assert.assertEquals(mile2.movieMap.get(1), mile2.movies[0]);
    }

    public void testBadArgsExit() throws Exception {
        Milestone2 mile2 = new Milestone2(new String[]{"F", "x", "gradStudent"});
        String s = "no can't do";
        String text = tapSystemOut(() -> {
                    mile2.badArgsExit(s);
                });
        Assert.assertEquals(s, text.trim());
    }

    public void testGenre_check() throws IOException {
        Milestone2 mile2 = new Milestone2(new String[]{"M", "20", "doctor", "war|drama"});
        boolean check = mile2.genre_check(new String[]{"int2k", "war", "art"}, false);
        Assert.assertEquals(check, false);
        boolean check1 = mile2.genre_check(new String[]{"children's", "war", "Documentary", "war", "Fantasy"}, false);
        Assert.assertEquals(check1, true);
        boolean check2 = mile2.genre_check(new String[]{}, false);
        Assert.assertEquals(check2, false);
    }

    public void testAnalyseRating() {
    }

    public void testCalculateAvgScore() {
    }

    public void testFind_relevant_movies() {
    }

    public void testSolve() throws Exception {
        Milestone2 mile2_A = new Milestone2(new String[]{"F", "25", "gradStudent", "action|comedy"});
        Milestone2 mile2_B = new Milestone2(new String[]{"F", "25", "gradStudent", "action|comedy"});
        String text1 = tapSystemOut(mile2_A::solve);
        String text2 = tapSystemOut(mile2_B::solve);
        Assert.assertEquals(text1, text2);
    }

}