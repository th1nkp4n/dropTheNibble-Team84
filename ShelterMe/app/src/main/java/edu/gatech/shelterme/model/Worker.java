package edu.gatech.shelterme.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

/**
 * Created by ttsubota3 on 2/18/18.
 */

public class Worker extends User  implements Serializable{
    private String social;
    private String key;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private String email;
    private String pass;
    private String name;


    public Worker(){
        super("dave", "steve", "mario");
        this.email = super.getEmail();
        this.pass = super.getPass();
        this.name = super.getName();
    }

    public String getSocial() {
        return social;
    }
    public String getKey() {
        return key;
    }
    public void setSocial(String number, String key) {
        ref.child("worker").child(key).child("social").setValue(number);
        this.social = number;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void updateEmail(String email) {
        ref.child("worker").child(this.key).child("email").setValue(email);
        this.setEmail(email);
    }

    public void setEmail(String email, String key) {
        ref.child("worker").child(key).child("email").setValue(email);
        this.email = email;
    }

    @Override
    public void updateName(String name) {
        ref.child("worker").child(this.key).child("name").setValue(name);
        this.setName(name);
    }

    public void setName(String name, String key) {
        ref.child("worker").child(key).child("name").setValue(name);
        this.name = name;
    }

    @Override
    public void updatePassword(String password) {
        ref.child("worker").child(this.key).child("password").setValue(password);
        this.setPass(password);
    }

    public void setPassword(String password, String key) {
        ref.child("worker").child(key).child("pass").setValue(password);
        this.pass = password;
    }
}
