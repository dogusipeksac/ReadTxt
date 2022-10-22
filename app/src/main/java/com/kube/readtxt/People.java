package com.kube.readtxt;

public class People {
    private int personID;
    private String name;
    private String gender;
    private int weight;
    private int height;
    private int dateOfBirtday;//dogum tarihi
    private int dailyCalorieNeeds;
    private int burnedCalorie;
    private int takenCalorie;


    public int getTakenCalorie() {
        return takenCalorie;
    }

    public void setTakenCalorie(int takenCalorie) {
        this.takenCalorie = takenCalorie;
    }

    public int getBurnedCalorie() {
        return burnedCalorie;
    }

    public void setBurnedCalorie(int burnedCalorie) {
        this.burnedCalorie = burnedCalorie;
    }

    public People() {
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getDailyCalorieNeeds() {
        return dailyCalorieNeeds;
    }

    public void setDailyCalorieNeeds(int dailyCalorieNeeds) {
        this.dailyCalorieNeeds = dailyCalorieNeeds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDateOfBirtday() {
        return dateOfBirtday;
    }

    public void setDateOfBirtday(int dateOfBirtday) {
        this.dateOfBirtday = dateOfBirtday;
    }


}
