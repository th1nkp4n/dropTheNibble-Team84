package edu.gatech.shelterme.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Admin;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.User;
import edu.gatech.shelterme.model.Worker;

public class RegistrationRolePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_role_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button cancelButton = (Button) findViewById(R.id.registrationRoleCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Log", "cancel from registration role");
                Intent intent = new Intent(getBaseContext(), LoginPage.class);
                startActivity(intent);
            }
        }) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton admin = (RadioButton) findViewById(R.id.adminRadio);
                RadioButton homeless = (RadioButton) findViewById(R.id.guestRadio);
                RadioButton worker = (RadioButton) findViewById(R.id.workerRadio);
                Log.d("Log", "User type selected");
                Intent intent = new Intent(getBaseContext(), RegistrationUserInfo.class);
                User user = null;
                if (homeless.isChecked()) {
                    user = new Homeless();
                } else if (worker.isChecked()) {
                    user  = new Worker();
                } else if (admin.isChecked()) {
                    user  = new Admin();
                }
                intent.putExtra("user", user);

                startActivity(intent);
            }
        });
    }

}
