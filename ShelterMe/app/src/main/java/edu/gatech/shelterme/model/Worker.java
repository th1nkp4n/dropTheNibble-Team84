package edu.gatech.shelterme.model;


/**
 * Created by ttsubota3 on 2/18/18.
 */

public class Worker extends User {
    String social;
    public Worker(String number) {
        social = number;
    }
    public Worker(){}
    String getSocial() {
        return social;
    }
}
