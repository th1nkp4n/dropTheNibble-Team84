package edu.gatech.shelterme.model;

/**
 * Created by ttsubota3 on 4/13/18.
 */

public class WorkerSocial {
    public String correctSocial(String number) {
        String result;
        if (number.length() < 9) {
            result = "number is too short";
        } else if (number.length() > 9) {
            result = "number is too long";
        } else {
            result = "valid number";
        }
        return result;
    }
}
