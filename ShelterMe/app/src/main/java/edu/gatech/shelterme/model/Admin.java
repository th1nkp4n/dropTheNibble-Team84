package edu.gatech.shelterme.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

/**
 * Created by ttsubota3 on 2/18/18.
 */

public class Admin extends User implements Serializable{
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private String key;
    private String email;
    private String pass;
    private String name;

    public Admin() {
        super("jim", "france", "kill");
        this.email = super.getEmail();
        this.pass = super.getPass();
        this.name = super.getName();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public void updateEmail(String email) {
        ref.child("admin").child(this.key).child("email").setValue(email);
        this.setEmail(email);
    }

    public void setEmail(String email, String key) {
        this.email = email;
        ref.child("admin").child(key).child("email").setValue(email);
    }

    @Override
    public void updateName(String name) {
        ref.child("admin").child(this.key).child("name").setValue(name);
        this.setName(name);
    }

    public void setName(String name, String key) {
        this.name = name;
        ref.child("admin").child(key).child("name").setValue(name);
    }

    @Override
    public void updatePassword(String password) {
        ref.child("admin").child(this.key).child("password").setValue(password);
        this.setPass(password);
    }

    public void setPassword(String password, String key) {
        this.pass = password;
        ref.child("admin").child(key).child("pass").setValue(password);
    }

}
