package edu.gatech.shelterme.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ttsubota3 on 2/18/18.
 */

public class Worker extends User {
    private String social;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();


    public Worker(){}
    public String getSocial() {
        return social;
    }
    public void setSocial(String number) {
        ref.child("workerUsers").child(this.getEmail()).child("social").setValue(social);
    }

    @Override
    public void updateEmail(String email) {
        ref.child("workerUsers").child(this.getEmail()).child("email").setValue(email);
    }

    @Override
    public void updateName(String name) {
        ref.child("workerUsers").child(this.getEmail()).child("name").setValue(name);
    }

    @Override
    public void updatePassword(String password) {
        ref.child("workerUsers").child(this.getEmail()).child("password").setValue(password);
    }
}
