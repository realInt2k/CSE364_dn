package com.data;
// aka: user: UserID::Gender::Age::Occupation::Zip-code
public class Reviewer{
    public char gender;
    public int age, ID, occupation;
    public String zipCode; // *may* be separated by dash "-"
    public Reviewer() {
    }
    public void input(String line) {
        String[] subLine = line.split("::");
        this.ID = Integer.parseInt(subLine[0]);
        this.gender = subLine[1].charAt(0);
        this.age = Integer.parseInt(subLine[2]);
        this.occupation = Integer.parseInt(subLine[3]);
        this.zipCode = subLine[4];
    }
}


