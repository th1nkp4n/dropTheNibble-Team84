package edu.gatech.shelterme.controllers;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Admin;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.User;
import edu.gatech.shelterme.model.Worker;

public class RegistrationUserInfo extends AppCompatActivity {

    private Button continueButton;
    private Button cancelButton;
    private EditText userField;
    private EditText pass1Field;
    private EditText emailField;
    private EditText pass2Field;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user_info);

        userField = (EditText) findViewById(R.id.registration_user_info_name);
        emailField = (EditText) findViewById(R.id.registration_user_info_email);
        pass1Field = (EditText) findViewById(R.id.registration_user_info_password1);
        pass2Field = (EditText) findViewById(R.id.registration_user_info_password2);
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
                    String key = (String) getIntent().getSerializableExtra("key");
                    String type = (String) getIntent().getSerializableExtra("type");
                    Intent intent;
                    if (type.equals("admin")) {
                        Log.d("Log","Admin");
                        Admin user = null;
                        ref.child("admin").child(key)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // Get Post object and use the values to update the UI
                                        Admin user = (Admin) dataSnapshot.getValue(Admin.class);
                                        Log.d("Log", user.toString());
                                        Log.d("Log", emailField.getText().toString());
                                        user.setEmail(emailField.getText().toString(), key);
                                        user.setPassword(pass1Field.getText().toString(), key);
                                        user.setName(userField.getText().toString(), key);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.d("Log", "didn't work");
                                    }
                                });
                        intent = new Intent(getBaseContext(), HomepageMap.class);
                        intent.putExtra("type", "admin");
                        intent.putExtra("key", key);
                    } else if (type.equals("homeless")) {
                        Log.d("Log","Homeless");
                        Homeless user = null;
                        FirebaseDatabase.getInstance().getReference().child("homeless").child(key)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // Get Post object and use the values to update the UI
                                        Homeless user = (Homeless) dataSnapshot.getValue(Homeless.class);
                                        user.setEmail(emailField.getText().toString(), key);
                                        user.setPassword(pass1Field.getText().toString(), key);
                                        user.setName(userField.getText().toString(), key);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                        intent = new Intent(getBaseContext(), Homeless_Registration.class);
                        intent.putExtra("key", key);
                    } else {
                        Log.d("Log","Worker");
                        Worker user = null;
                        FirebaseDatabase.getInstance().getReference().child("worker").child(key)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // Get Post object and use the values to update the UI
                                        Worker user = (Worker) dataSnapshot.getValue(Worker.class);
                                        user.setEmail(emailField.getText().toString(), key);
                                        user.setPassword(pass1Field.getText().toString(), key);
                                        user.setName(userField.getText().toString(), key);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                        intent = new Intent(getBaseContext(), WorkerRegistration.class);
                        intent.putExtra("key", key);
                    }
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
