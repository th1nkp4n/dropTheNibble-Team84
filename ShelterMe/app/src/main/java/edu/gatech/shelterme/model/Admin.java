package edu.gatech.shelterme.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ttsubota3 on 2/18/18.
 */

public class Admin extends User {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    public Admin() {}

    @Override
    public void updateEmail(String email) {
        ref.child("adminUsers").child(this.getEmail()).child("email").setValue(email);
    }

    @Override
    public void updateName(String name) {
        ref.child("adminUsers").child(this.getEmail()).child("name").setValue(name);
    }

    @Override
    public void updatePassword(String password) {
        ref.child("adminUsers").child(this.getEmail()).child("password").setValue(password);
    }
}
