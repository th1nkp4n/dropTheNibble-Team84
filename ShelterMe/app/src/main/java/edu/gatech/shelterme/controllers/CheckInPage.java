package edu.gatech.shelterme.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by KKhosla on 3/11/18.
 */

public class CheckInPage extends AppCompatActivity {
    private DatabaseReference shelterReference;
    TextView noFamilies;
    TextView noIndividuals;
    TextView numfamiliesText;
    TextView numIndividualsText;
    EditText numIndividuals;
    EditText numFamilies;
    Button confirm;
    Button cancel;


    // Take in user - store hte shelter ID and the num of individuals/families that checked in
    // Update the vacancies based on how many people check in
    // Use the vacancies to determine the maximum of each type that can check in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail__page);
        final int shelterID = (int) getIntent().getIntExtra("id",0);
        noIndividuals = (TextView) findViewById(R.id.noIndividualsText);
        noFamilies = (TextView) findViewById(R.id.noFamiliesText);
        numIndividualsText = (TextView) findViewById(R.id.restrictions);
        numfamiliesText = (TextView) findViewById(R.id.longitude);
        numIndividuals = (EditText) findViewById(R.id.checkInFamilyNumberInput);
        numFamilies = (EditText) findViewById(R.id.checkInIndividualNumberInput);
        confirm = (Button) findViewById(R.id.checkInConfirmButton);
        cancel = (Button) findViewById(R.id.checkInCancelButton);

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
                Intent start = new Intent(getBaseContext(), Shelter_detail_Page.class);
                startActivity(start);
            }
        });

    }
}
