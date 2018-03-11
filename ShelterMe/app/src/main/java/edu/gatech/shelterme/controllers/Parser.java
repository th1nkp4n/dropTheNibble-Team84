package edu.gatech.shelterme.controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.util.Log;

import edu.gatech.shelterme.model.*;


// Make a method that populates firebase - use imputstreamreader and bufferedreader - pass in the context
// Look up AssetManager - takes current context and gets assets from it
// Hashmap? - map name of shelter (or ID), value is a string that has all of hte attributes
// Convert hasmap strings to Shelter object attributes
// Dump shelter objects into Firebase

/**
 * Created by KKhosla on 2/25/18.
 */

public class Parser {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    public void parseData(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream stream = assetManager.open("Homeless Shelter Database.csv");
        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(streamReader);
        reader.readLine();
        String line = reader.readLine();
        while (line != null) {
            Shelter shelter = new Shelter();
            String delim = ",";
            String[] tokens = line.split(delim);
            Log.d("id", tokens[0]);

            String id = tokens[0];
            String name = tokens[1];
            String capacity = tokens[2];
            String restrictions = tokens[3];
            String longString = tokens[4];
            Double longitude = Double.parseDouble(longString);
            String latString = tokens[5];
            Double latitude = Double.parseDouble(latString);
            String address = tokens[6];
            String notes = tokens[7];
            String phone = tokens[8];

            shelter.setName(name);
            shelter.setCapacity(capacity);
            shelter.setRestriction(restrictions);
            shelter.setLongitude(longitude);
            shelter.setLatitude(latitude);
            shelter.setAddress(address);
            shelter.setSpecialNotes(notes);
            shelter.setPhone(phone);

            ref.child("shelters").child(id).setValue(shelter);

            line = reader.readLine();
        }
        stream.close();
        streamReader.close();
        reader.close();
    }
}
