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
    Homeless homeless;
    String currentSingleCapacity;
    String currentFamilyCapacity;
    String currentSingleVancancies;
    String currentFamilyVancancies;


    // Update the vacancies based on how many people check in
    // Use the vacancies to determine the maximum of each type that can check in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail__page);
        String shelterID = (String) getIntent().getStringExtra("id");
        noSingles = (TextView) findViewById(R.id.noIndividualsText);
        noFamilies = (TextView) findViewById(R.id.noFamiliesText);
        numSinglesText = (TextView) findViewById(R.id.restrictions);
        numFamiliesText = (TextView) findViewById(R.id.longitude);
        numSingles = (EditText) findViewById(R.id.checkInFamilyNumberInput);
        numFamilies = (EditText) findViewById(R.id.checkInIndividualNumberInput);
        confirm = (Button) findViewById(R.id.checkInConfirmButton);
        cancel = (Button) findViewById(R.id.checkInCancelButton);
        homeless = (Homeless) getIntent().getSerializableExtra("homeless");

        ref.child("shelters").child(shelterID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        currentSingleCapacity = (String) dataSnapshot.child("singleCapacity").getValue();
                        currentFamilyCapacity = (String) dataSnapshot.child("familyCapacity").getValue();
                        currentSingleVancancies = (String) dataSnapshot.child("singleVacancies").getValue();
                        currentFamilyVancancies = (String) dataSnapshot.child("familyVacancies").getValue();
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

                ref.child("shelters").child(shelterID)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                homeless.setFamiles(Integer.valueOf(numFamilies.getText().toString()), homeless.getKey());
                                homeless.setSingles(Integer.valueOf(numSingles.getText().toString()), homeless.getKey());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("Log", "didn't work");
                            }
                        });

                ref.child("homeless").child(homeless.getKey())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                homeless.setCheckedIn(Integer.valueOf(shelterID), homeless.getKey());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("Log", "didn't work");
                            }
                        });


                Log.d("CHECKOUT", "User checked into " + homeless.getCheckedIn());

                Intent start = new Intent(getBaseContext(), Shelter_detail_Page.class);
                startActivity(start);
            }
        });

    }
}
