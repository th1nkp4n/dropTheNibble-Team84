package edu.gatech.shelterme.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class Admin {
    final static private FirebaseDatabase database = FirebaseDatabase.getInstance();
    final static private DatabaseReference ref = database.getReference();
    private String email;
    private String pass;
    private String name;

    public Admin() {
        this.name = "name";
    }

    public String getEmail() {
        return email;
    }

    public static void setEmail(String email, String key) {
        ref.child("admin").child(key).child("email").setValue(email);
    }


    public static void setName(String name, String key) {
        ref.child("admin").child(key).child("name").setValue(name);
    }

//    public String getName() {
//        return name;
    //}


    public static void setPass(String password, String key) {
        ref.child("admin").child(key).child("pass").setValue(password);
    }

    public String getPass() {
        return pass;
    }

}
