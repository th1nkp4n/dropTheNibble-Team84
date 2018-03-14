package edu.gatech.shelterme.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chiwk on 2/18/2018.
 */

public class Homeless extends User {
    private int age;
    private String gender;
    private boolean veteran;
    private int checkedIn;
    private int famlies;
    private int singles;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    public static List<String> genders = Arrays.asList("Cis Woman", "Cis Man", "Nonbinary",
            "Trans Woman", "Trans Man", "Other", "Prefer Not To Say");

    public Homeless() {
        this.checkedIn = -1;
        this.famlies = 0;
        this.singles = 0;
    }


    public int getAge() {return age;}
    public String getGender() {return gender;}
    public boolean getVeteran() {return veteran;}
    public int getCheckedIn() {return checkedIn;}
    public int getFamilies() {return famlies;}
    public int getSingles() {return singles;}
    public void setAge(int page, String key) {
        ref.child("homelessUsers").child(key).child("age").setValue(page);
    }
    public void setGender(String pgender, String key) {
        ref.child("homelessUsers").child(key).child("gender").setValue(pgender);
    }
    public void setVeteran(boolean pvet, String key) {
        ref.child("homelessUsers").child(key).child("vet").setValue(pvet);
    }
    public void setCheckedIn(int shelter, String key) {
        ref.child("homelessUsers").child(key).child("checkedIn").setValue(shelter);
    }
    public void setFamiles(int pfamlies, String key) {
        ref.child("homelessUsers").child(key).child("famlies").setValue(pfamlies);
    }
    public void setSingles(int psingles, String key) {
        ref.child("homelessUsers").child(key).child("singles").setValue(psingles);
    }

    @Override
    public void updateEmail(String email) {
        ref.child("homelessUsers").child(this.getEmail()).child("email").setValue(email);
    }

    @Override
    public void updateName(String name) {
        ref.child("homelessUsers").child(this.getEmail()).child("name").setValue(name);
    }

    @Override
    public void updatePassword(String password) {
        ref.child("homelessUsers").child(this.getEmail()).child("password").setValue(password);
    }




}
