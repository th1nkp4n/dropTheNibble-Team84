package edu.gatech.shelterme.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.Shelter;

public class Search extends AppCompatActivity {
    private Button submitButton;
    private Button cancelButton;
    private Spinner genderSpinner;
    private Spinner ageSpinner;
    private Spinner nameSpinner;

    private ArrayList<String> genderArray;
    private ArrayAdapter<String> genders;
    private ArrayList<String> ageArray;
    private ArrayAdapter<String> age;
    private ArrayList<String> nameArray;
    private ArrayAdapter<String> names;
    private DatabaseReference shelterReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        submitButton = (Button) findViewById(R.id.filter);
        cancelButton = (Button) findViewById(R.id.cancel);
        genderSpinner = (Spinner) findViewById(R.id.gender);
        ageSpinner = (Spinner) findViewById(R.id.age);
        nameSpinner = (Spinner) findViewById(R.id.name);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(getBaseContext(), HomepageMap.class);
                getIntent().putExtra("cancel", 5);
                startActivity(start);
            }
        });

        genderArray = new ArrayList<>();
        genderArray.add("Any Gender");
        genderArray.add("Women");
        genderArray.add("Men");

        genders = new ArrayAdapter(this,android.R.layout.simple_spinner_item, genderArray);
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genders);

        ageArray = new ArrayList<>();
        ageArray.add("Any Age");
        ageArray.add("Newborns");
        ageArray.add("Children");
        ageArray.add("Young Adults");

        age = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, ageArray);
        age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(age);

        nameArray = new ArrayList<>();
        nameArray.add("Any Shelter");
        shelterReference = FirebaseDatabase.getInstance().getReference()
                .child("shelters");
        shelterReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int length = (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    Log.d("CCurrent shelter:", dsp.getValue(Shelter.class).toString());
                    //Log.d("CCurrent type:", dsp.getValue(Shelter.class).getClass().toString());
                    nameArray.add(dsp.getValue(Shelter.class).toString());
                    //shelterName.add(dsp.getValue(Shelter.class).getName());
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                //Toast.makeText(PostDetailActivity.this, "Failed to load post.",
                //Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        });
        names = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, nameArray);
        names.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(names);
    }
}
