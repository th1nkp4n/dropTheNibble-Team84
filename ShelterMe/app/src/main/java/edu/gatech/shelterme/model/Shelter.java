package edu.gatech.shelterme.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Meha on 2/25/2018.
 */

public class Shelter {
    private String name;
    private String capacity;
    private String restriction;
    private double longitude;
    private double latitude;
    private String address;
    private String specialNotes;
    private String phone;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    public Shelter(){};

    public Shelter(String name, String capacity, String restriction, double longitude, double latitude, String address, String specialNotes, String phone) {
        this.name = name;
        this.capacity = capacity;
        this.restriction = restriction;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.specialNotes = specialNotes;
        this.phone = phone;
    }



    public String getName() {
        return name;
    }

    public void setName(String name, String id) {
        ref.child("homelessUsers").child(id).child("name").setValue(name);
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity, String id) {
        ref.child("homelessUsers").child(id).child("capacity").setValue(capacity);
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction, String id) {
        ref.child("homelessUsers").child(id).child("restriction").setValue(restriction);
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude, String id) {
        ref.child("homelessUsers").child(id).child("longitude").setValue(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude, String id) {
        ref.child("homelessUsers").child(id).child("latitude").setValue(latitude);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address, String id) {
        ref.child("homelessUsers").child(id).child("address").setValue(address);
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes, String id) {
        ref.child("homelessUsers").child(id).child("specialNotes").setValue(specialNotes);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone, String id) {
        ref.child("homelessUsers").child(id).child("phone").setValue(phone);
    }

    public String toString() {
        return name;
    }
}
