package com.help;

public abstract class UserDir {
    public void print() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }
    public static String alternative = null;
    public static String get() {
        if(alternative == null)
            return  System.getProperty("user.dir");
        else
            return alternative;
    }
}
