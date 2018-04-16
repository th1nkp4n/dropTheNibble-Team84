package edu.gatech.shelterme.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class Homeless {
    private int age;
    private String gender;
    private boolean veteran;
    private int checkedIn;
    private int families;
    private int singles;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private static FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    private static DatabaseReference ref2 = database2.getReference();
    private String email;
    private String pass;
    private String name;

    final public static List<String> genders = Arrays.asList("Cis Woman", "Cis Man", "Nonbinary",
            "Trans Woman", "Trans Man", "Other", "Prefer Not To Say");

    public Homeless() {
        this.checkedIn = -1;
        this.families = 0;
        this.singles = 0;
    }

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


    public static void setCheckedIn(int shelter, String key) {
        ref2.child("homeless").child(key).child("checkedIn").setValue(shelter);
        //this.checkedIn = shelter;
    }

    public int getCheckedIn() {return checkedIn;}

    public static void setFamilies(int families, String key) {
        ref2.child("homeless").child(key).child("families").setValue(families);
    }

    public int getFamilies() {return families;}

    public static void setSingles(int singles, String key) {
        ref2.child("homeless").child(key).child("singles").setValue(singles);
    }

    public int getSingles() {return singles;}

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
