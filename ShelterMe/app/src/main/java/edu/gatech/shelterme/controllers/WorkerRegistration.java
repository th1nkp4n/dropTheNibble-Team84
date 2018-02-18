package edu.gatech.shelterme.controllers;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.shelterme.R;

public class WorkerRegistration extends AppCompatActivity {
    //Widgets
    private EditText socialSecurity;
    private Button registerButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_registration);

        //Grab the dialog widgets so we can get info for later
        socialSecurity = (EditText) findViewById(R.id.social_security_number);
        registerButton = (Button) findViewById(R.id.register_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(getBaseContext(), LoginPage.class);
                startActivity(start);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(socialSecurity.getText().length() != 9) {
                    Log.d("Log", "Please enter your social security number");
                } else {
                    Log.d("Log", "correct inputs");

                    Intent home = new Intent(getBaseContext(), HomepageMap.class);
                    startActivity(home);
                }
            }
        });
    }
}
