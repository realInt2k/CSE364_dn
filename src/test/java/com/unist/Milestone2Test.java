package com.unist;

import com.data.Movie;
import com.data.Person;
import com.help.UserDir;
import junit.framework.TestCase;
import org.json.JSONException;
import org.junit.Assert;

import javax.sound.midi.MidiEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

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
        boolean check3 = mile2.genre_check(new String[]{"Beka"}, true);
        Assert.assertEquals(check3, false);
    }

    public void testAnalyseRating() {
    }

    public void testCalculateAvgScore() {
    }

    public void testFind_relevant_movies() {
    }

    public void test_no_gender_age_preference() throws Exception{
        Milestone2 mile2 = new Milestone2(new String[]{"", "", "Superhero", "crime"});
        String text = "Finding movies for a customer with: \n" +
                "\n" +
                "\tgender: no preference \n" +
                "\tage: no preference\n" +
                "\toccupation: Superhero\n" +
                "No such occupation found, use 'other' as default\n" +
                "\tGenres: crime \n";
        Assert.assertEquals(mile2.welcome.toString(), text);
    }

    public void test_unknown_genre() throws Exception{
        Milestone2 mile2 = new Milestone2(new String[]{"F", "25", "gradStudent", "Astralis"});
        Assert.assertEquals(mile2.badArgs, true);
    }

    public void test_transgender() throws Exception{
        Milestone2 mile2 = new Milestone2(new String[]{"T", "25", "gradStudent", "crime"});
        Assert.assertEquals(mile2.badArgs, true);
    }

    public void test_four_args_no_genre()throws Exception{
        Milestone2 mile2 = new Milestone2(new String[]{"F", "25", "gradStudent", ""});
        Assert.assertEquals(mile2.badArgs, true);
    }

    public void testSolve() throws Exception {
        Milestone2 mile2_A = new Milestone2(new String[]{"F", "25", "gradStudent", "action|comedy|War"});
        Milestone2 mile2_B = new Milestone2(new String[]{"F", "25", "gradStudent", "comedy|action|War|Action"});
        String text1 = tapSystemOut(mile2_A::solve);
        String text2 = tapSystemOut(mile2_B::solve);
        Assert.assertEquals(text1, text2);
    }

    public void testToo_many_args() throws Exception{
        Milestone2 mile2 = new Milestone2(new String[]{"F", "23", "gradStudent", "aciton", "Netflix"});
        Assert.assertEquals(mile2.badArgs, true);
    }

    public void testFields() throws IOException {
        Milestone2 dungn = new Milestone2(new String[]{"F", "25", "gradStudent", "action|comedy|War"});
        Person x = new Person(new String[] {"action", "comedy", "War"});
        dungn.prepareData();

        Milestone2 int2k = new Milestone2(new String[]{"M", "25", "gradStudent", "action|comedy|War"});
        Person y = new Person(new String[] {"action", "comedy", "War"});
        int2k.prepareData();

        Assert.assertEquals(dungn.parseOccupation.entrySet(), int2k.parseOccupation.entrySet());
        for(Map.Entry<Integer, Movie> e : dungn.movieMap.entrySet())
        {
            Assert.assertEquals(e.getValue().title, int2k.movieMap.get(e.getKey()).title);
        }
    }

    public void testSmallerThan10() throws IOException {
        Milestone2 dungn = new Milestone2(new String[]{"F", "25", "gradStudent", "action|comedy|War"});
        Person x = new Person(new String[] {});
        dungn.prepareData();
        dungn.filterData(x);
        Assert.assertEquals(dungn.movies.length, 10);
    }

    public void testDefaultConstructor() throws IOException {
        Milestone2 dungn = new Milestone2();
        Assert.assertEquals(dungn.movieMap.get(1), null);
    }

    public void testNoOccupation() throws IOException {
        Milestone2 dungn = new Milestone2(new String[]{"F", "25", "", "action|comedy|War"});
        dungn.prepareData();
    }

    public void noMovie() throws IOException, JSONException {
        Milestone2 dungn = new Milestone2(new String[]{"F", "25", "", "action|comedy|War"});
        dungn.prepareData();
        //dungn.movies = new Movie[1];
        //dungn.movies[0].input("3007::American Movie (1999)::Documentary");
        dungn.movies[0].ID = 9999;
        dungn.solve();
    }
}