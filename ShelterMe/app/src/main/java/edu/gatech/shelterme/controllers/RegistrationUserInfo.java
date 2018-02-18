package edu.gatech.shelterme;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationUserInfo extends AppCompatActivity {

    private Button continueButton;
    private Button cancelButton;
    private EditText userField;
    private EditText pass1Field;
    private EditText emailField;
    private EditText pass2Field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user_info);

        continueButton = (Button) findViewById(R.id.registration_user_info_continue);
        cancelButton = (Button) findViewById(R.id.registration_user_info_cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Log", "Registration cancelled");
                Intent intent = new Intent(getBaseContext(), LoginPage.class);
                startActivity(intent);
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass1Field.getText().toString().compareTo(pass2Field.getText().toString())==0) {
                    Log.d("Log", "Valid registration information");
                    Intent intent = new Intent(getBaseContext(), HomepageMap.class);
                    startActivity(intent);
                } else {
                    //tell them they had the wrong username or password
                    Log.d("Log", "incorrect inputs");
                    BadLoginAlertDialogFragment badLogin = new BadLoginAlertDialogFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    badLogin.show(ft, "maybe");
                }

            }
        });
    }


}
