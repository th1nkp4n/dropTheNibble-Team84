package edu.gatech.shelterme.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.Shelter;

/**
 * Created by KKhosla on 3/11/18.
 */

public class CheckInPage extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    TextView noFamilies;
    TextView noSingles;
    TextView numFamiliesText;
    TextView numSinglesText;
    EditText numSingles;
    EditText numFamilies;
    Button confirm;
    Button cancel;
    String homeless;
    long currentSingleCapacity;
    long currentFamilyCapacity;
    long currentSingleVancancies;
    long currentFamilyVancancies;


    // Update the vacancies based on how many people check in
    // Use the vacancies to determine the maximum of each type that can check in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_in_page);
        String shelterID = getIntent().getSerializableExtra("id").toString();
        noSingles = findViewById(R.id.noIndividualsText);
        noFamilies = findViewById(R.id.noFamiliesText);
        numSinglesText = findViewById(R.id.restrictions);
        numFamiliesText = findViewById(R.id.longitude);
        numSingles = findViewById(R.id.checkInFamilyNumberInput);
        numFamilies = findViewById(R.id.checkInIndividualNumberInput);
        confirm = findViewById(R.id.checkInConfirmButton);
        cancel = findViewById(R.id.checkInCancelButton);
        homeless = (String) getIntent().getSerializableExtra("type");

        ref.child("shelters").child(shelterID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        currentSingleCapacity = (long) dataSnapshot.child("singleCapacity").getValue();
                        currentFamilyCapacity = (long) dataSnapshot.child("familyCapacity").getValue();
                        currentSingleVancancies = (long) dataSnapshot.child("singleVacancies").getValue();
                        currentFamilyVancancies = (long) dataSnapshot.child("familyVacancies").getValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Log", "didn't work");
                    }
                });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(getBaseContext(), Shelter_detail_Page.class);
                startActivity(start);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Firebase Stuff

                Shelter shelt = null;
                ref.child("shelters")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Shelter shelt = null;
                                int counter = Integer.valueOf(shelterID);
                                for (DataSnapshot shelterSnapshot: dataSnapshot.getChildren()) {
                                    counter--;
                                    if (counter == -1) {
                                        shelt = (Shelter) shelterSnapshot.getValue(Shelter.class);
                                    }
                                }
                                int num1 = Integer.valueOf(numFamilies.getText().toString());
                                int num2 = Integer.valueOf(numSingles.getText().toString());
                                shelt.setFamilyVacancies(shelt.getFamilyVacancies() - num1, shelterID);
                                shelt.setSingleVacancies(shelt.getSingleVacancies() - num2, shelterID);
                                Log.d("Singles capacity: ", ((Integer) shelt.getSingleCapacity()).toString());
                                Log.d("Families capacity: ", ((Integer) shelt.getFamilyCapacity()).toString());



                                //shelterID.setFamiles(Integer.valueOf(numFamilies.getText().toString()), getIntent().getSerializableExtra("key").toString());
                                //shelterID.setSingles(Integer.valueOf(numSingles.getText().toString()), getIntent().getSerializableExtra("key").toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("Log", "didn't work");
                            }
                        });

                ref.child("homeless")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String key = getIntent().getStringExtra("key");
                                Homeless homelessPerson = null;
                                for (DataSnapshot homelessSnap: dataSnapshot.getChildren()) {
                                    if ((homelessSnap.getKey().equals(key))) {
                                        homelessPerson = (Homeless) homelessSnap.getValue(Homeless.class);
                                    }
                                }
                                Log.d("Shelter: ", shelterID);
                                Log.d("Key: ", getIntent().getStringExtra("key"));
                                homelessPerson.setCheckedIn(Integer.valueOf(shelterID), getIntent().getStringExtra("key"));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("Log", "didn't work");
                            }
                        });


                // Log.d("CHECKOUT", "User checked into " + homeless.getCheckedIn());

                Intent start = new Intent(getBaseContext(), Shelter_detail_Page.class);
                start.putExtra("type",getIntent().getSerializableExtra("type"));
                start.putExtra("key",getIntent().getSerializableExtra("key"));
                start.putExtra("id", shelterID);
                startActivity(start);
            }
        });

    }
}
