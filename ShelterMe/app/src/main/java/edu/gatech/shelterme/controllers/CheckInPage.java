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
    final private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private TextView noFam;
    private TextView famCheck;
    private TextView noInd;
    private TextView indCheck;
    private EditText numFam;
    private EditText numInd;
    private Button cancel;
    private Button confirm;
    private int shelterID;
    private String type;
    private String key;
    private long famVacancies;
    private long indVacancies;
    private boolean hasFam;
    private boolean hasInd;
    private int goodCheckIn; // 1 when good, 0 when they entered 0, -1 when they exceeded capacity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_page);

        noFam =findViewById(R.id.noFam);
        famCheck = findViewById(R.id.famCheck);
        noInd = findViewById(R.id.noInd);
        indCheck = findViewById(R.id.indCheck);
        numFam = findViewById(R.id.numFam);
        numInd = findViewById(R.id.numInd);
        cancel = findViewById(R.id.checkInCancel);
        confirm = findViewById(R.id.checkInConfirm);
        shelterID = getIntent().getIntExtra("id", 0);
        type = getIntent().getStringExtra("type");
        key = getIntent().getStringExtra("key");
        goodCheckIn = 0;
        confirm.setVisibility(View.INVISIBLE);



        ref.child("shelters").child(Integer.toString(shelterID))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        famVacancies = (long) dataSnapshot.child("familyVacancies").getValue();
                        indVacancies = (long) dataSnapshot.child("singleVacancies").getValue();
                        hasFam = famVacancies > 0;
                        hasInd = indVacancies > 0;

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

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent start = new Intent(getBaseContext(), Shelter_detail_Page.class);
                start.putExtra("type", type);
                start.putExtra("id", shelterID);
                start.putExtra("key", getIntent().getSerializableExtra("key"));
                startActivity(start);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                ref.child("shelters").child(Integer.toString(shelterID))
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
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
                                        Homeless homelessPerson = new Homeless();
                                        homelessPerson.setCheckedIn(-1, key);

                                    } else if (newVac < 0) {
                                        goodCheckIn = -1;
                                        Log.d("log: ","over family cap");
                                        BadFamilyCheckInAlertDialogFragment badCheckIn = new BadFamilyCheckInAlertDialogFragment();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        badCheckIn.show(ft, "maybe");
                                        //newVac = shelter.getFamilyVacancies() - numFV;
                                    } else {
                                        goodCheckIn = 1;
                                        Homeless person = new Homeless();
                                        person.setFamilies(numFV, key);
                                        shelter.setFamilyVacancies(newVac, Integer.toString(shelterID));
                                        Log.d("Families vacancy: ", ((Integer) shelter.getFamilyVacancies()).toString());
                                    }
                                }
                                if (numInd.getVisibility() == View.VISIBLE) {
                                    int numIV = Integer.valueOf(numInd.getText().toString());
                                    int newVac = shelter.getSingleVacancies() - numIV;
                                    if (numIV == 0) {
                                        goodCheckIn = goodCheckIn;
                                        Log.d("log :", "didn't check in any individuals");
                                        Homeless homelessPerson = new Homeless();
                                        homelessPerson.setCheckedIn(-1, key);
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
                                        Homeless person = new Homeless();
                                        person.setSingles(numIV, key);
                                        shelter.setSingleVacancies(newVac, Integer.toString(shelterID));
                                        Log.d("Singles vacancy: ", ((Integer) shelter.getSingleVacancies()).toString());
                                    }
                                }
                                if (goodCheckIn == 1) {
                                    goodCheckIn = 0;
                                    Homeless person = new Homeless();
                                    person.setCheckedIn(shelterID, key);
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
                        });

            }
        });

    }
}
