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


    public Worker(){
        super(null, null, null);
        DatabaseReference usersRef = ref.child("worker");
        usersRef.push();
        this.key = usersRef.getKey();
    }
    public String getSocial() {
        return social;
    }
    public String getKey() {
        return key;
    }
    public void setSocial(String number) {
        ref.child("worker").child(this.key).child("social").setValue(social);
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

    @Override
    public void updateName(String name) {
        ref.child("worker").child(this.key).child("name").setValue(name);
        this.setName(name);
    }

    @Override
    public void updatePassword(String password) {
        ref.child("worker").child(this.key).child("password").setValue(password);
        this.setPass(password);
    }
}
