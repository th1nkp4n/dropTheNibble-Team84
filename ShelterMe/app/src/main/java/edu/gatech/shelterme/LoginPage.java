package edu.gatech.shelterme;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Meha on 2/10/2018.
 */

public class LoginPage extends AppCompatActivity {
    /* ************************
            Widgets we will need for binding and getting information
         */
    private EditText userField;
    private EditText passField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         * Grab the dialog widgets so we can get info for later
         */
        userField = (EditText) findViewById(R.id.loginpage_username_input);
        passField = (EditText) findViewById(R.id.loginpage_password_input);

    }

    /**
     * Button handler for the add new student button
     * @param view the button
     */
    public void onAddPressed(View view) {
        Log.d("Edit", "Add Student");

        if(userField.getText().toString());
        _student.setMajor((String) majorSpinner.getSelectedItem());
        _student.setClassStanding((ClassStanding) classStandingSpinner.getSelectedItem());

        Log.d("Edit", "Got new student data: " + _student);
        if (!editing) {
            model.addStudent(_student);
        }  else {
            model.replaceStudentData(_student);
        }

        finish();
    }

}

