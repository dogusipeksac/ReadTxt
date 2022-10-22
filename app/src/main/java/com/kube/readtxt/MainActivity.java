package com.kube.readtxt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    ArrayList<People> peopleArrayList;
    ArrayList<People> newPeopleArrayListForMonitoring;
    ArrayList<Food> foodArrayList;
    ArrayList<Sport> sportArrayList;
    ArrayList<String> monitoringArrayList;
    int currentYear = 2022;
    int takenCalorie = 0;
    int burnedCalorie = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        upgradeLists();


    }

    public void init() {
        peopleArrayList = new ArrayList<>();
        foodArrayList = new ArrayList<>();
        sportArrayList = new ArrayList<>();
        monitoringArrayList = new ArrayList<>();
        newPeopleArrayListForMonitoring = new ArrayList<>();
    }

    public void upgradeLists() {
        //dosya okumaları
        convertTextFileToModelList("people.txt");
        convertTextFileToModelList("food.txt");
        convertTextFileToModelList("sport.txt");
        convertTextFileToModelList("command.txt");
    }

    public void convertTextFileToModelList(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName), "UTF-8"));
            // do reading, usually loop until end of file reading
            String mLine = null;
            if (fileName.equalsIgnoreCase("people.txt")) {
                while ((mLine = reader.readLine()) != null) {
                    //process line
                    String[] data = mLine.split("\\s+");
                    People people = new People();
                    people.setPersonID(Integer.parseInt(data[0]));
                    people.setName(data[1]);
                    people.setGender(data[2]);
                    people.setWeight(Integer.parseInt(data[3]));
                    people.setHeight(Integer.parseInt(data[4]));
                    people.setDateOfBirtday(Integer.parseInt(data[5]));
                    people.setDailyCalorieNeeds(calculationDailyCalorie(people.getHeight(), people.getWeight(), currentYear - people.getDateOfBirtday(), people.getGender()));
                    peopleArrayList.add(people);

                }
            } else if (fileName.equalsIgnoreCase("food.txt")) {
                //process line
                while ((mLine = reader.readLine()) != null) {
                    String[] data = mLine.split("\\s+");
                    Food food = new Food();
                    food.setFoodID(Integer.parseInt(data[0]));
                    food.setNameOfFood(data[1]);
                    food.setCalorieCount(Integer.parseInt(data[2]));
                    foodArrayList.add(food);
                }
            }
            else if (fileName.equalsIgnoreCase("sport.txt")) {
                while ((mLine = reader.readLine()) != null) {
                    //process line
                    String[] data = mLine.split("\\s+");
                    Sport sport = new Sport();
                    sport.setSportID(Integer.parseInt(data[0]));
                    sport.setNameOfSport(data[1]);
                    sport.setCalorieBurned(Integer.parseInt(data[2]));
                    sportArrayList.add(sport);
                }
            }
            else if (fileName.equalsIgnoreCase("command.txt")) {
                boolean success = false;
                while ((mLine = reader.readLine()) != null) {
                    if (mLine.contains("print(")) {
                        //id sayıları 5 üzerinden yapılmıştır
                        String data = mLine.substring("print(".length(), "print(".length() + 5);
                        System.out.println("data" + data);
                        for (int i = 0; i < peopleArrayList.size(); i++) {
                            if (peopleArrayList.get(i).getPersonID() == Integer.parseInt(data)) {
                                monitoringArrayList.add(peopleArrayList.get(i).getName()
                                        + "     "
                                        + (currentYear - peopleArrayList.get(i).getDateOfBirtday())
                                        + "     "
                                        + peopleArrayList.get(i).getDailyCalorieNeeds()
                                        + " kcal"
                                        + "     "
                                        + peopleArrayList.get(i).getTakenCalorie()
                                        + " kcal"
                                        + "     "
                                        + peopleArrayList.get(i).getBurnedCalorie()
                                        + " kcal"
                                        + "     "
                                        + (peopleArrayList.get(i).getTakenCalorie() - peopleArrayList.get(i).getDailyCalorieNeeds()) + " kcal");
                                monitoringArrayList.add("*************");
                            }

                        }

                    } else if (mLine.contains("printList")) {
                        for (int i = 0; i < newPeopleArrayListForMonitoring.size(); i++) {
                            monitoringArrayList.add(newPeopleArrayListForMonitoring.get(i).getName()
                                    + "     "
                                    + (currentYear - newPeopleArrayListForMonitoring.get(i).getDateOfBirtday())
                                    + "     "
                                    + newPeopleArrayListForMonitoring.get(i).getDailyCalorieNeeds()
                                    + " kcal"
                                    + "     "
                                    + newPeopleArrayListForMonitoring.get(i).getTakenCalorie()
                                    + " kcal"
                                    + "     "
                                    + newPeopleArrayListForMonitoring.get(i).getBurnedCalorie()
                                    + " kcal"
                                    + "     "
                                    + (newPeopleArrayListForMonitoring.get(i).getTakenCalorie() - newPeopleArrayListForMonitoring.get(i).getDailyCalorieNeeds()) + " kcal");

                        }
                        monitoringArrayList.add("*************");

                    } else if (mLine.contains("printWarn")) {
                       if (newPeopleArrayListForMonitoring!=null&&newPeopleArrayListForMonitoring.size()!=0){
                           for (int i=0;i<newPeopleArrayListForMonitoring.size();i++){
                               if (newPeopleArrayListForMonitoring.get(i).getDailyCalorieNeeds()<(newPeopleArrayListForMonitoring.get(i).getTakenCalorie()-newPeopleArrayListForMonitoring.get(i).getBurnedCalorie())){
                                   monitoringArrayList.add("there is no such person");
                                   monitoringArrayList.add("*************");
                               }
                           }

                       }
                    } else {
                        //process line
                        String[] data = mLine.split("\\s+");
                        for (int i = 0; i < peopleArrayList.size(); i++) {
                            if (peopleArrayList.get(i).getPersonID() == Integer.parseInt(data[0])) {
                                for (int y = 0; y < foodArrayList.size(); y++) {
                                    if (foodArrayList.get(y).getFoodID() == Integer.parseInt(data[1])) {
                                        int totalTakenCalorie = foodArrayList.get(y).getCalorieCount() * Integer.parseInt(data[2]);
                                        monitoringArrayList.add(peopleArrayList.get(i).getPersonID() + " has taken " + totalTakenCalorie + " kcal from " + foodArrayList.get(y).getNameOfFood());
                                        takenCalorie = totalTakenCalorie;
                                        peopleArrayList.get(i).setTakenCalorie(takenCalorie + peopleArrayList.get(i).getTakenCalorie());
                                        takenCalorie = 0;
                                        monitoringArrayList.add("*************");
                                    }
                                }
                                for (int z = 0; z < sportArrayList.size(); z++) {
                                    if (sportArrayList.get(z).getSportID() == Integer.parseInt(data[1])) {
                                        int totalBurnedCalorie = sportArrayList.get(z).getCalorieBurned() * Integer.parseInt(data[2]);
                                        monitoringArrayList.add(peopleArrayList.get(i).getPersonID() + " has burned " + totalBurnedCalorie + " kcal thanks to " + sportArrayList.get(z).getNameOfSport());
                                        burnedCalorie = totalBurnedCalorie;
                                        peopleArrayList.get(i).setBurnedCalorie(burnedCalorie + peopleArrayList.get(i).getBurnedCalorie());
                                        burnedCalorie = 0;
                                        monitoringArrayList.add("*************");
                                    }
                                }
                                if (newPeopleArrayListForMonitoring.size() == 0) {
                                    newPeopleArrayListForMonitoring.add(peopleArrayList.get(i));
                                } else {
                                    if (!newPeopleArrayListForMonitoring.contains(peopleArrayList.get(i))) {
                                        newPeopleArrayListForMonitoring.add(peopleArrayList.get(i));
                                    }
                                }

                            }
                        }
                    }

                }


            }

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        showLists();
    }

    public void showLists() {
       /* for (int i = 0; i < foodArrayList.size(); i++)
            System.out.println("Food:Model=" + i + ":Name:" + foodArrayList.get(i).getNameOfFood() + "\n");

        for (int i = 0; i < peopleArrayList.size(); i++)
            System.out.println("People:Model=" + i + ":NeedCalorie:" + peopleArrayList.get(i).getDailyCalorieNeeds() + "\n");

        for (int i = 0; i < sportArrayList.size(); i++)
            System.out.println("Sport:Model=" + i + ":Name:" + sportArrayList.get(i).getNameOfSport() + "\n");*/

        for (int i = 0; i < monitoringArrayList.size(); i++)
            System.out.println(monitoringArrayList.get(i) + "\n");
    }

    public int calculationDailyCalorie(int height, int weight, int age, String gender) {
        if (gender.equalsIgnoreCase("male")) {
            return (int) (66 + ((weight * 13.75) + (5 * height) - (6.8 * age)));
        } else {
            return (int) (665 + ((weight * 9.6) + (1.7 * height) - (4.7 * age)));
        }
    }

}