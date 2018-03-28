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

    public Admin() {
        super(null, null, null);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public void updateEmail(String email) {
        ref.child("adminUsers").child(this.key).child("email").setValue(email);
        this.setEmail(email);
    }

    @Override
    public void updateName(String name) {
        ref.child("adminUsers").child(this.key).child("name").setValue(name);
        this.setName(name);
    }

    @Override
    public void updatePassword(String password) {
        ref.child("adminUsers").child(this.key).child("password").setValue(password);
        this.setPass(password);
    }

//    public void create() {
//        DatabaseReference usersRef = ref.child("admin");
//        DatabaseReference d = usersRef.push();
//        d.setValue(this);
//        this.key = d.getKey();
//    }
}
