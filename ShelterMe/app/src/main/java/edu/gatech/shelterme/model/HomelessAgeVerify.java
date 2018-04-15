package edu.gatech.shelterme.model;

/**
 * Created by Meha on 4/15/2018.
 */

public class HomelessAgeVerify {
    public static String ageIsValid(int age) {
        if (age > 0) {
            return "good age!";
        } else if (age < 0) {
            return "bad age!";
        } else {
            return "newborn!";
        }
    }
}
