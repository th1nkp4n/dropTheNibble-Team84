package edu.gatech.shelterme.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by ttsubota3 on 2/18/18.
 */

public class Worker {
    private String social;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private String email;
    private String pass;
    private String name;


    public Worker(){
        this.social = "0";
    }

    public String getSocial() {
        return social;
    }


    public void setSocial(String number, String key) {
        ref.child("worker").child(key).child("social").setValue(number);
        this.social = number;
    }


    public void setEmail(String email, String key) {
        ref.child("worker").child(key).child("email").setValue(email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setName(String name, String key) {
        ref.child("worker").child(key).child("name").setValue(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setPass(String password, String key) {
        ref.child("worker").child(key).child("pass").setValue(password);
        this.pass = password;
    }

    public String getPass() {
        return pass;
    }
}
