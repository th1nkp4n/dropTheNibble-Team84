package edu.gatech.shelterme.model;

/**
 * Created by Meha on 2/18/2018.
 */

public abstract class User {
    private String email;
    public String password;

    public String getEmail() {return email;}
    public String getPass() {return password;}
    public void setEmail(String pemail) {email = pemail;}
    public void setPass(String ppass) {password = ppass;}
}
