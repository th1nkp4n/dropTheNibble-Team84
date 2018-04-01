package edu.gatech.shelterme.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chiwk on 2/18/2018.
 */

public class Homeless {
    private int age;
    private String gender;
    private boolean veteran;
    private int checkedIn;
    private int families;
    private int singles;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private String email;
    private String pass;
    private String name;

    public static List<String> genders = Arrays.asList("Cis Woman", "Cis Man", "Nonbinary",
            "Trans Woman", "Trans Man", "Other", "Prefer Not To Say");

    public Homeless() {
        this.checkedIn = -1;
        this.families = 0;
        this.singles = 0;
    }


    public int getAge() {return age;}
    public String getGender() {return gender;}
    public boolean getVeteran() {return veteran;}
    public int getCheckedIn() {return checkedIn;}
    public int getFamilies() {return families;}
    public int getSingles() {return singles;}
    public void setAge(int page, String key) {
        ref.child("homeless").child(key).child("age").setValue(page);
        this.age = page;
    }
    public void setGender(String pGender, String key) {
        ref.child("homeless").child(key).child("gender").setValue(pGender);
        this.gender = pGender;
    }
    public void setVeteran(boolean pVet, String key) {
        ref.child("homeless").child(key).child("veteran").setValue(pVet);
        this.veteran = pVet;
    }
    public void setCheckedIn(int shelter, String key) {
        ref.child("homeless").child(key).child("checkedIn").setValue(shelter);
        this.checkedIn = shelter;
    }
    public void setFamilies(int pFamilies, String key) {
        ref.child("homeless").child(key).child("families").setValue(pFamilies);
        this.families = pFamilies;
    }
    public void setSingles(int pSingles, String key) {
        ref.child("homeless").child(key).child("singles").setValue(pSingles);
        this.singles = pSingles;
    }


    public void setEmail(String email, String key) {
        ref.child("homeless").child(key).child("email").setValue(email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setName(String name, String key) {
        ref.child("homeless").child(key).child("name").setValue(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setPass(String password, String key) {
        ref.child("homeless").child(key).child("pass").setValue(password);
        this.pass = password;
    }

    public String getPass() {
        return pass;
    }


}
