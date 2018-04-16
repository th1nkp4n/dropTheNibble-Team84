package edu.gatech.shelterme.controllers;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.Shelter;

public class CheckInPage extends AppCompatActivity {
    private static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_page);


        TextView noFam =findViewById(R.id.noFam);
        TextView famCheck = findViewById(R.id.famCheck);
        TextView noInd = findViewById(R.id.noInd);
        TextView indCheck = findViewById(R.id.indCheck);
        EditText numFam = findViewById(R.id.numFam);
        EditText numInd = findViewById(R.id.numInd);
        Button cancel = findViewById(R.id.checkInCancel);
        Button confirm = findViewById(R.id.checkInConfirm);
        int shelterID = getIntent().getIntExtra("id", 0);
        String type = getIntent().getStringExtra("type");
        String key = getIntent().getStringExtra("key");

        confirm.setVisibility(View.INVISIBLE);

        ref.child("shelters").child(Integer.toString(shelterID))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long famVacancies = (long) dataSnapshot.child("familyVacancies").getValue();
                        long indVacancies = (long) dataSnapshot.child("singleVacancies").getValue();
                        boolean hasFam = famVacancies > 0;
                        boolean hasInd = indVacancies > 0;

                        if (hasFam) {
                            noFam.setVisibility(View.INVISIBLE);
                            Log.d("log: ", "hasFam");
                        } else {
                            numFam.setVisibility(View.INVISIBLE);
                            famCheck.setVisibility(View.INVISIBLE);
                            Log.d("log: ", "noFam");
                        }

                        if (hasInd) {
                            noInd.setVisibility(View.INVISIBLE);
                            Log.d("log: ", "hasInd");
                        } else {
                            numInd.setVisibility(View.INVISIBLE);
                            indCheck.setVisibility(View.INVISIBLE);
                            Log.d("log: ","noInd");
                        }

                        if (hasInd || hasFam) {
                            confirm.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Failure: ", "Check In Firebase get didn't work");
                    }
                });

        cancel.setOnClickListener((view)-> {
                Intent start = new Intent(getBaseContext(), Shelter_detail_Page.class);
                start.putExtra("type", type);
                start.putExtra("id", shelterID);
                start.putExtra("key", getIntent().getSerializableExtra("key"));
                startActivity(start);
            });

        confirm.setOnClickListener(( view)->

                ref.child("shelters").child(Integer.toString(shelterID))
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int goodCheckIn = 0; // 1 when good, 0 when they entered 0, -1 when they exceeded capacity
                                Shelter shelter = dataSnapshot.getValue(Shelter.class);
                                if (shelter == null) {
                                    return;
                                }
                                if (numFam.getVisibility() == View.VISIBLE) {
                                    int numFV = Integer.valueOf(numFam.getText().toString());
                                    int newVac = shelter.getFamilyVacancies() - numFV;
                                    if (numFV == 0){
                                        goodCheckIn = 0;
                                        Log.d("log :", "didn't check in any families");
                                        Homeless.setCheckedIn(-1, key);

                                    } else if (newVac < 0) {
                                        goodCheckIn = -1;
                                        Log.d("log: ","over family cap");
                                        BadFamilyCheckInAlertDialogFragment badCheckIn = new BadFamilyCheckInAlertDialogFragment();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        badCheckIn.show(ft, "maybe");
                                        //newVac = shelter.getFamilyVacancies() - numFV;
                                    } else {
                                        goodCheckIn = 1;
                                        Homeless.setFamilies(numFV, key);
                                        Shelter.setFamilyVacancies(newVac, Integer.toString(shelterID));
                                        Log.d("Families vacancy: ", ((Integer) shelter.getFamilyVacancies()).toString());
                                    }
                                }
                                if (numInd.getVisibility() == View.VISIBLE) {
                                    int numIV = Integer.valueOf(numInd.getText().toString());
                                    int newVac = shelter.getSingleVacancies() - numIV;
                                    if (numIV == 0) {
                                        goodCheckIn = goodCheckIn;
                                        Log.d("log :", "didn't check in any individuals");
                                        Homeless.setCheckedIn(-1, key);
                                    } else if (newVac < 0) {
                                        goodCheckIn = -1;
                                        Log.d("log: ","over individual cap");
                                        BadSingleCheckInAlertDialogFragment badCheckIn = new BadSingleCheckInAlertDialogFragment();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        badCheckIn.show(ft, "maybe");
                                    } else {
                                        if (goodCheckIn != -1) {
                                            goodCheckIn = 1;
                                        }
                                        Homeless.setSingles(numIV, key);
                                        Shelter.setSingleVacancies(newVac, Integer.toString(shelterID));
                                        Log.d("Singles vacancy: ", ((Integer) shelter.getSingleVacancies()).toString());
                                    }
                                }
                                if (goodCheckIn == 1) {
                                    goodCheckIn = 0;
                                    Homeless.setCheckedIn(shelterID, key);
                                    Intent start = new Intent(getBaseContext(), Shelter_detail_Page.class);
                                    start.putExtra("id", shelterID);
                                    start.putExtra("key", key);
                                    start.putExtra("type", type);
                                    startActivity(start);
                                } else if (goodCheckIn == 0) {
                                    Intent start = new Intent(getBaseContext(), Shelter_detail_Page.class);
                                    start.putExtra("type", type);
                                    start.putExtra("id", shelterID);
                                    start.putExtra("key", getIntent().getSerializableExtra("key"));
                                    startActivity(start);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("log: ", "shelter part didn't work.");
                            }
                        })

            );

    }
}
