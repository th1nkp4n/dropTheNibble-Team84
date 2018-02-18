package edu.gatech.shelterme.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chiwk on 2/18/2018.
 */

public class Homeless extends User {
    private int age;
    private String gender;
    private boolean veteran;

    public static List<String> genders = Arrays.asList("Cis Woman", "Cis Man", "Nonbinary",
            "Trans Woman", "Trans Man", "Other", "Prefer Not To Say");

    public int getAge() {return age;}
    public String getGender() {return gender;}
    public boolean getVeteran() {return veteran;}
    public void setAge(int page) {age = page;}
    public void setGender(String pgender) {gender = pgender;}
    public void setVeteran(boolean pvet) {veteran = pvet;}

}
