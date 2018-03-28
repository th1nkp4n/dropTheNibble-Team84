package edu.gatech.shelterme.model;

import java.io.Serializable;

/**
 * Created by Meha on 2/18/2018.
 */

public abstract class User implements Serializable {
    private String email;
    private String password;
    private String name;
    public User() {}
    public User(String email, String password, String name) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPass() {return password;}
    public void setName(String pname) {name = pname;}
    public void setEmail(String pemail) {email = pemail;}
    public void setPass(String ppass) {password = ppass;}
    public void updateEmail(String email) {}
    public void updateName(String name) {}
    public void updatePassword(String password) {}

    @Override
    public String toString() {
        return name;
    }
}
