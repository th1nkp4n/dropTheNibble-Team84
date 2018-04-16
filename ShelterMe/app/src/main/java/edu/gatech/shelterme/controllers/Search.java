package edu.gatech.shelterme.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Shelter;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        DatabaseReference shelterReference;

        Button submitButton = findViewById(R.id.filter);
        Button cancelButton = findViewById(R.id.cancel);
        Spinner genderSpinner = findViewById(R.id.gender);
        Spinner ageSpinner = findViewById(R.id.age);
        Spinner nameSpinner = findViewById(R.id.name);

        ArrayList<String> genderArray = new ArrayList<>();
        genderArray.add("Any Gender");
        genderArray.add("Women");
        genderArray.add("Men");

        ArrayAdapter<String> genders = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, genderArray);
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genders);

        ArrayList<String> ageArray = new ArrayList<>();
        ageArray.add("Any Age");
        ageArray.add("Families w/ newborns");
        ageArray.add("Children");
        ageArray.add("Young adults");

        ArrayAdapter<String> age = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, ageArray);
        age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(age);

        ArrayList<String> nameArray = new ArrayList<>();
        nameArray.add("Any Shelter");
        shelterReference = FirebaseDatabase.getInstance().getReference()
                .child("shelters");
        shelterReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    //Log.d("CCurrent shelter:", dsp.getValue(Shelter.class).toString());
                    //Log.d("CCurrent type:", dsp.getValue(Shelter.class).getClass().toString());
                    Shelter val = dsp.getValue(Shelter.class);
                    if (val == null) {
                        return;
                    }
                    if(val.toString() != null) {
                        nameArray.add(val.toString());
                    }

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
        ArrayAdapter<String> names = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, nameArray);
        names.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(names);

        submitButton.setOnClickListener((View v) -> {
                Intent start = new Intent(getBaseContext(), HomepageMap.class);
                start.putExtra("name", nameSpinner.getSelectedItem().toString());
                start.putExtra("age", ageSpinner.getSelectedItem().toString());
                start.putExtra("gender", genderSpinner.getSelectedItem().toString());

                start.putExtra("key", getIntent().getStringExtra("key"));
                start.putExtra("id", getIntent().getIntExtra("id", 0));
                start.putExtra("type", getIntent().getStringExtra("type"));
                start.putExtra("filter", 1);
                startActivity(start);
        });

        cancelButton.setOnClickListener((View v) -> {
                Intent start = new Intent(getBaseContext(), HomepageMap.class);
                start.putExtra("filter", 0);
                start.putExtra("key", getIntent().getStringExtra("key"));
                start.putExtra("id", getIntent().getIntExtra("id", 0));
                start.putExtra("type", getIntent().getStringExtra("type"));
                startActivity(start);
        });
    }
}
