package com.data;
//  UserID::Gender::Age::Occupation::Zip-code
public class Person {
    private Character gender;
    private Integer age, occupation;
    private void init() {
        this.gender = null;
        this.age = null;
        this.occupation = null;
    }
    public Character getGender() {
        return this.gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }
    public Integer getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Integer getOccupation() {
        return this.occupation;
    }
    public void setOccupation(int occupation){
        this.occupation = occupation;
    }

    public String[] genre;
    public Person() {
        this.genre = null;
        this.init();
    }
    public Person(String[] genre) {
        this.genre = genre.clone();
        this.init();
    }
    public void setGenre(String[] genre) {
        this.genre = genre.clone();
    }
}
