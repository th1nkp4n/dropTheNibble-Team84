package edu.gatech.shelterme.controllers;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.User;

public class Homeless_Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText ageField;
    private Spinner genderSpinner;
    private Spinner veteranSpinner;
    private Button cancelButton;
    private Button nextButton;
    private Homeless _person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeless__registration);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        //Grab the dialog widgets so we can get info for later
        ageField = (EditText) findViewById(R.id.age_input);
        genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        veteranSpinner = (Spinner) findViewById(R.id.veteran_spinner);
        cancelButton = (Button) findViewById(R.id.homeless_registration_cancel);
        nextButton = (Button) findViewById(R.id.homeless_registration_next);

        ArrayAdapter<String> genders = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Homeless.genders);
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genders);

        ArrayAdapter<String> veteran = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arrays.asList(true, false));
        veteran.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        veteranSpinner.setAdapter(veteran);

//        if (getIntent().hasExtra(CourseDetailFragment.ARG_STUDENT_ID)) {
//            _person = (Homeless) getIntent().getParcelableExtra();
//        } else {
//            _person = new Homeless();
//        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Log", "valid inputs");
                Homeless user = (Homeless) getIntent().getSerializableExtra("user");
                user.setAge(Integer.valueOf(ageField.getText().toString()));
                user.setGender((String) genderSpinner.getSelectedItem());
                user.setVeteran((boolean) veteranSpinner.getSelectedItem());

                Intent intent = new Intent(getBaseContext(), HomepageMap.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //_major = "NA";
    }
}
