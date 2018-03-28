package edu.gatech.shelterme.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

/**
 * Created by ttsubota3 on 2/18/18.
 */

public class Admin {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private String email;
    private String pass;
    private String name;

    public Admin() {
        this.name = "killMe";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email, String key) {
        this.email = email;
        ref.child("admin").child(key).child("email").setValue(email);
    }


    public void setName(String name, String key) {
        this.name = name;
        ref.child("admin").child(key).child("name").setValue(name);
    }

    public String getName() {
        return name;
    }


    public void setPass(String password, String key) {
        this.pass = password;
        ref.child("admin").child(key).child("pass").setValue(password);
    }

    public String getPass() {
        return pass;
    }

}
