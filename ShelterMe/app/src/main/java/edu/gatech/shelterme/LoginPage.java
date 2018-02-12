package edu.gatech.shelterme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private Button loginButton;
    private Button registerButton;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        //Grab the dialog widgets so we can get info for later
        userField = (EditText) findViewById(R.id.loginpage_username_input);
        passField = (EditText) findViewById(R.id.loginpage_password_input);
        loginButton = (Button) findViewById(R.id.loginpage_login_button);
        registerButton = (Button) findViewById(R.id.loginpage_register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((userField.getText().toString().compareTo("user")==0)
                        && (passField.getText(). toString().compareTo("pass")==0)) {
                    Log.d("Log", "correct inputs");
                    Intent intent = new Intent(getBaseContext(), HomepageMap.class);
                    startActivity(intent);
                } else {
                    //tell them they had the wrong username or password
                    Log.d("Log", "incorrect inputs");
                    builder = new AlertDialog.Builder(getBaseContext());
                    builder.setTitle("Incorrect Login Information")
                            .setMessage("Your user name or password is incorrect.")
                            .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }

            }
        });
    }

    /**
     * Button handler for the add new student button
     * @param view the button
     */
    /*public void onLoginPressed(View view) {
        Log.d("Login", "login info");


        if(userField.getText().toString() =="user"&& passField.getText(). toString() =="pass") {
                Log.d("Log", "correct inputs");
                setContentView(R.layout.activity_homepage_map);
        } else {
            //tell them they had the wrong username or password
        }

        finish();
    }*/

}

