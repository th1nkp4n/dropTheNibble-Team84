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
import edu.gatech.shelterme.model.User;

/**
 * Created by KKhosla on 3/11/18.
 */

public class CheckInPage extends AppCompatActivity {
    private DatabaseReference shelterReference;
    TextView noFamilies;
    TextView noSingles;
    TextView numfamiliesText;
    TextView numSinglesText;
    EditText numSingles;
    EditText numFamilies;
    Button confirm;
    Button cancel;
    User user;


    // Take in user - store hte shelter ID and the num of individuals/families that checked in
    // Update the vacancies based on how many people check in
    // Use the vacancies to determine the maximum of each type that can check in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail__page);
        final int shelterID = (int) getIntent().getIntExtra("id",0);
        noSingles = (TextView) findViewById(R.id.noIndividualsText);
        noFamilies = (TextView) findViewById(R.id.noFamiliesText);
        numSinglesText = (TextView) findViewById(R.id.restrictions);
        numfamiliesText = (TextView) findViewById(R.id.longitude);
        numSingles = (EditText) findViewById(R.id.checkInFamilyNumberInput);
        numFamilies = (EditText) findViewById(R.id.checkInIndividualNumberInput);
        confirm = (Button) findViewById(R.id.checkInConfirmButton);
        cancel = (Button) findViewById(R.id.checkInCancelButton);
        user = (User) getIntent().getSerializableExtra("user");

        shelterReference = FirebaseDatabase.getInstance().getReference()
                .child("shelters");

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
                Log.d("CHECKOUT", "User checked into " + user.getShelter().toString());
                user.setCheckedIn(shelterID);
                user.setFamilies(Integer.valueOf(numFamilies.getText().toString()));
                user.setSingles(Integer.valueOf(numSingles.getText().toString()));
                Intent start = new Intent(getBaseContext(), Shelter_detail_Page.class);
                startActivity(start);
            }
        });

    }
}
