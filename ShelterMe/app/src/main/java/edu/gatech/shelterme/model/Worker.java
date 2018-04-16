package edu.gatech.shelterme.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Worker {
    final static private FirebaseDatabase database = FirebaseDatabase.getInstance();
    final static private DatabaseReference ref = database.getReference();
    final private String email;
    private String pass;
    private String name;


    public Worker(){
        this.email = "email";
    }




    public static void setSocial(String number, String key) {
        ref.child("worker").child(key).child("social").setValue(number);
    }


    public static void setEmail(String email, String key) {
        ref.child("worker").child(key).child("email").setValue(email);
    }

    public String getEmail() {
        return email;
    }


    public static void setName(String name, String key) {
        ref.child("worker").child(key).child("name").setValue(name);
    }



    public static void setPass(String password, String key) {
        ref.child("worker").child(key).child("pass").setValue(password);
    }

    public String getPass() {
        return pass;
    }


}
