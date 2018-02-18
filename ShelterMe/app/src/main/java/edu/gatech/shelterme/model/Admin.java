package edu.gatech.shelterme.model;

import edu.gatech.shelterme.User;

/**
 * Created by ttsubota3 on 2/18/18.
 */

public class Admin extends User {
    String name;
    public Admin(String name) {
        this.name = name;
    }
    String getName() {
        return name;
    }
}
