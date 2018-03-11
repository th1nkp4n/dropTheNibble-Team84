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
    private Shelter checkedIn;
    private int famlies;
    private int singles;

    public static List<String> genders = Arrays.asList("Cis Woman", "Cis Man", "Nonbinary",
            "Trans Woman", "Trans Man", "Other", "Prefer Not To Say");

    public Homeless() {
        this.checkedIn = null;
        this.famlies = 0;
        this.singles = 0;
    }


    public int getAge() {return age;}
    public String getGender() {return gender;}
    public boolean getVeteran() {return veteran;}
    public Shelter getCheckedIn() {return checkedIn;}
    public int getFamilies() {return famlies;}
    public int getSingles() {return singles;}
    public void setAge(int page) {age = page;}
    public void setGender(String pgender) {gender = pgender;}
    public void setVeteran(boolean pvet) {veteran = pvet;}
    public void setCheckedIn(Shelter shelter) {checkedIn = shelter;}
    public void setFamiles(int pfamlies) {famlies = pfamlies;}
    public void setSingles(int psingles) {singles = psingles;}




}
