package edu.gatech.shelterme.model;



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
