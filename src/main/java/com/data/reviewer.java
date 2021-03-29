package com.data;
// aka: user: UserID::Gender::Age::Occupation::Zip-code
public class reviewer {
    public char gender;
    public int age, ID, occupation, zipCode;
    public void input(String line) {
        String[] subLine = line.split("::");
        this.ID = Integer.parseInt(subLine[0]);
        this.gender = subLine[1].charAt(0);
        this.age = Integer.parseInt(subLine[2]);
        this.occupation = Integer.parseInt(subLine[3]);
        this.zipCode = Integer.parseInt(subLine[4]);
    }
}
