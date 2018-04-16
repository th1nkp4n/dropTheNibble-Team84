package edu.gatech.shelterme.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class Homeless {
    final private int checkedIn;
    final private int families;
    final private int singles;
    final private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    final private static DatabaseReference ref = database.getReference();
    final private static FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    final private static DatabaseReference ref2 = database2.getReference();
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

    public static void setAge(int page, String key) {
        ref.child("homeless").child(key).child("age").setValue(page);
    }



    public static void setGender(String pGender, String key) {
        ref.child("homeless").child(key).child("gender").setValue(pGender);
    }


    public static void setVeteran(boolean pVet, String key) {
        ref.child("homeless").child(key).child("veteran").setValue(pVet);
    }


    public static void setCheckedIn(int shelter, String key) {
        ref2.child("homeless").child(key).child("checkedIn").setValue(shelter);
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

    public static void setEmail(String email, String key) {
        ref.child("homeless").child(key).child("email").setValue(email);
    }

    public String getEmail() {
        return email;
    }


    public static void setName(String name, String key) {
        ref.child("homeless").child(key).child("name").setValue(name);
    }

    public String getName() {
        return name;
    }


    public static void setPass(String password, String key) {
        ref.child("homeless").child(key).child("pass").setValue(password);
    }

    public String getPass() {
        return pass;
    }


}
