package com.help;

public class userDir {
    public void print() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }
    public String get() {
        return  System.getProperty("user.dir");
    }
}
