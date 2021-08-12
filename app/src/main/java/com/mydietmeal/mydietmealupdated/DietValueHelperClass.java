package com.mydietmeal.mydietmealupdated;

public class DietValueHelperClass {
    String height, weight, age, gender, bmi, bmr;


    public DietValueHelperClass(String height, String weight, String age, String gender, String bmi, String bmr) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.bmi = bmi;
        this.bmr = bmr;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getBmr() {
        return bmr;
    }

    public void setBmr(String bmr) {
        this.bmr = bmr;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
