package edu.gatech.shelterme.model;

/**
 * Created by chiwk on 4/15/2018.
 */

public class AdminPasswordCheck {
    public static String passIsGood(String passwd) {
        if (passwd.matches("[A-Z].*[0-9]|[0-9].*[A-Z]")) {
            return "Good Password";
        } else if (!passwd.matches(".*[A-Z].*") && !passwd.matches(".*[0-9].*")) {
            return "Needs a capital letter and a number";
        } else if (!passwd.matches(".*[A-Z].*")) {
            return "Needs a capital letter";
        } else {
            return "Needs a number";
        }

    }
}