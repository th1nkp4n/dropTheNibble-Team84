package edu.gatech.shelterme.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chiwk on 2/18/2018.
 */

public class Homeless extends User implements Serializable{
    private int age;
    private String gender;
    private boolean veteran;
    private int checkedIn;
    private int famlies;
    private int singles;
    private String key;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    public static List<String> genders = Arrays.asList("Cis Woman", "Cis Man", "Nonbinary",
            "Trans Woman", "Trans Man", "Other", "Prefer Not To Say");

    public Homeless() {
        super(null, null, null);
        this.checkedIn = -1;
        this.famlies = 0;
        this.singles = 0;
        DatabaseReference usersRef = ref.child("homeless");
        usersRef.push();
        this.key = usersRef.getKey();
    }


    public int getAge() {return age;}
    public String getKey() {
        return key;
    }
    public String getGender() {return gender;}
    public boolean getVeteran() {return veteran;}
    public int getCheckedIn() {return checkedIn;}
    public int getFamilies() {return famlies;}
    public int getSingles() {return singles;}
    public void setAge(int page) {
        ref.child("homeless").child(this.key).child("age").setValue(page);
        this.age = page;
    }
    public void setGender(String pgender) {
        ref.child("homeless").child(this.key).child("gender").setValue(pgender);
        this.gender = pgender;
    }
    public void setVeteran(boolean pvet) {
        ref.child("homeless").child(this.key).child("vet").setValue(pvet);
        this.veteran = pvet;
    }
    public void setCheckedIn(int shelter) {
        ref.child("homeless").child(this.key).child("checkedIn").setValue(shelter);
        this.checkedIn = shelter;
    }
    public void setFamiles(int pfamlies) {
        ref.child("homeless").child(this.key).child("famlies").setValue(pfamlies);
        this.famlies = pfamlies;
    }
    public void setSingles(int psingles) {
        ref.child("homeless").child(this.key).child("singles").setValue(psingles);
        this.singles = psingles;
    }
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void updateEmail(String email) {
        ref.child("homeless").child(this.key).child("email").setValue(email);
        this.setEmail(email);
    }

    @Override
    public void updateName(String name) {
        ref.child("homeless").child(this.key).child("name").setValue(name);
        this.setName(name);
    }

    @Override
    public void updatePassword(String password) {
        ref.child("homeless").child(this.key).child("password").setValue(password);
        this.setPass(password);
    }




}
