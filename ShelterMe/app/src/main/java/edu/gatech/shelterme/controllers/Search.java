package edu.gatech.shelterme.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Homeless;

public class Search extends AppCompatActivity {
    private Button submitButton;
    private Button cancelButton;
    private Spinner genderSpinner;
    private Spinner ageSpinner;
    private Spinner nameSpinner;

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

        ArrayList<String> genderArray = new ArrayList<>();
        genderArray.add("Any Gender");
        genderArray.add("Women");
        genderArray.add("Men");

        ArrayAdapter<String> genders = new ArrayAdapter(this,android.R.layout.simple_spinner_item, genderArray);
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genders);

        ArrayList<String> ageArray = new ArrayList<>();
        ageArray.add("Any Age");
        ageArray.add("Newborns");
        ageArray.add("Children");
        ageArray.add("Young Adults");

        ArrayAdapter<String> age = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, ageArray);
        age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(age);

        ArrayList<String> nameArray = new ArrayList<>();
        
    }
}
