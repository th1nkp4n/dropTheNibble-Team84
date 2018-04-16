package edu.gatech.shelterme.controllers;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.User;

public class Homeless_Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText ageField;
    private Spinner genderSpinner;
    private Spinner veteranSpinner;
    final private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeless__registration);

        Button cancelButton;
        Button nextButton;

        //Grab the dialog widgets so we can get info for later
        ageField = findViewById(R.id.age_input);
        genderSpinner =  findViewById(R.id.gender_spinner);
        veteranSpinner = findViewById(R.id.veteran_spinner);
        cancelButton = findViewById(R.id.homeless_registration_cancel);
        nextButton = findViewById(R.id.homeless_registration_next);
        String key = (String) getIntent().getSerializableExtra("key");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("homeless").child(key)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FirebaseDatabase.getInstance().getReference().child("homeless").child(key).removeValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Intent start = new Intent(getBaseContext(), LoginPage.class);
                startActivity(start);
            }
        });

        ArrayAdapter<String> genders = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Homeless.genders);
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genders);

        ArrayAdapter<String> veteran = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arrays.asList(true, false));
        veteran.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        veteranSpinner.setAdapter(veteran);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = ageField.getText().toString();
                boolean ageValid;
                try {
                    int num = Integer.parseInt(number);
                    Log.i("",num+" is a number");
                    ageValid = true;
                } catch (NumberFormatException e) {
                    Log.i("",number+" is not a number");
                    ageValid = false;
                }
                if (ageValid) {
                    Log.d("Log", "valid inputs");
                    FirebaseDatabase.getInstance().getReference().child("homeless").child(key)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // Get Post object and use the values to update the UI
                                    Homeless user =  dataSnapshot.getValue(Homeless.class);
                                    if (user == null) {
                                        return;
                                    }
                                    user.setAge(Integer.valueOf(ageField.getText().toString()), key);
                                    user.setGender((String) genderSpinner.getSelectedItem(), key);
                                    user.setVeteran((boolean) veteranSpinner.getSelectedItem(), key);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                    Intent intent = new Intent(getBaseContext(), HomepageMap.class);
                    intent.putExtra("type", "homeless");
                    intent.putExtra("key", key);
                    startActivity(intent);
                } else {
                    BadHomelessRegistrationDialogFragment badReg = new BadHomelessRegistrationDialogFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    badReg.show(ft, "maybe");
                }
            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
