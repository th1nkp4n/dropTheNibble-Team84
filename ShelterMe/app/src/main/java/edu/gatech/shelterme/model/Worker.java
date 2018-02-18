package edu.gatech.shelterme.model;

import edu.gatech.shelterme.User;

/**
 * Created by ttsubota3 on 2/18/18.
 */

public class Worker extends User {
    String social;
    public Worker(String number) {
        social = number;
    }
    String getSocial() {
        return social;
    }
}
