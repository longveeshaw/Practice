package com.practice.redis.jedis.model;

import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = -441033312095126451L;

    private String uid;

    private int age;

    /**
     * m: male
     * f: female
     */
    private char gender;

    public User(){

    }

    public User(String uid, int age, char gender) {
        this.uid = uid;
        this.age = age;
        this.gender = gender;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
