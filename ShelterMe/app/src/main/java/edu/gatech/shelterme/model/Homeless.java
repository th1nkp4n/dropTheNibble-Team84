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
    private static FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    private static DatabaseReference ref2 = database2.getReference();
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
    public void setGender(String pgender, String key) {
        ref.child("homeless").child(key).child("gender").setValue(pgender);
        this.gender = pgender;
    }
    public void setVeteran(boolean pvet, String key) {
        ref.child("homeless").child(key).child("veteran").setValue(pvet);
        this.veteran = pvet;
    }
    public static void setCheckedIn(int shelter, String key) {
        ref2.child("homeless").child(key).child("checkedIn").setValue(shelter);
        //this.checkedIn = shelter;
    }
    public static void setFamilies(int pfamilies, String key) {
        ref2.child("homeless").child(key).child("families").setValue(pfamilies);
        //this.families = pfamilies;
    }
    public static void setSingles(int psingles, String key) {
        ref2.child("homeless").child(key).child("singles").setValue(psingles);
        // this.singles = psingles;
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
