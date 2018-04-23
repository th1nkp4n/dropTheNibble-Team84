package edu.gatech.shelterme.controllers;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Admin;
import edu.gatech.shelterme.model.EmailCheck;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.Worker;

public class RegistrationUserInfo extends AppCompatActivity {

    private EditText userField;
    private EditText pass1Field;
    private EditText emailField;
    private EditText pass2Field;
    final private FirebaseDatabase database = FirebaseDatabase.getInstance();
    final private DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user_info);
        Button continueButton;
        Button cancelButton;

        userField =  findViewById(R.id.registration_user_info_name);
        emailField =  findViewById(R.id.registration_user_info_email);
        pass1Field = findViewById(R.id.registration_user_info_password1);
        pass2Field =  findViewById(R.id.registration_user_info_password2);
        continueButton =  findViewById(R.id.registration_user_info_continue);
        cancelButton = findViewById(R.id.registration_user_info_cancel);
        String key = (String) getIntent().getSerializableExtra("key");
        String type = (String) getIntent().getSerializableExtra("type");

        cancelButton.setOnClickListener((view)-> {
                ref.child("admin").child(key)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FirebaseDatabase.getInstance().getReference().child(type).child(key).removeValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Log", "didn't work");
                    }
                });
                Log.d("Log", "Registration cancelled");
                Intent intent = new Intent(getBaseContext(), LoginPage.class);
                startActivity(intent);
            }
        );

        continueButton.setOnClickListener((view)-> {
                if (userField.length() == 0 ||emailField.length() == 0 || pass1Field.length() == 0
                        || pass2Field.length() == 0 || pass1Field.getText().toString()
                        .compareTo(pass2Field.getText().toString())!=0) {
                    Log.d("Log", "empty inputs");
                    BadRegistrationInputsDialogFragment badInputs = new BadRegistrationInputsDialogFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    badInputs.show(ft, "maybe");
                } else {
                    boolean isEmailValid = EmailCheck.testEmail(emailField.getText().toString());
                    if (!isEmailValid) {
                        BadRegistrationInputsDialogFragment badInputs = new BadRegistrationInputsDialogFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        badInputs.show(ft, "maybe");
                    } else {
                        Log.d("Log", "Valid registration information");
                        Intent intent = new Intent();
                        switch (type) {
                            case "admin":
                                Log.d("Log", "Admin");
                                ref.child("admin").child(key)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                // Get Post object and use the values to update the UI
                                                Admin user = dataSnapshot.getValue(Admin.class);
                                                if (user == null) {
                                                    return;
                                                }
                                                Log.d("Log", user.toString());
                                                Log.d("Log", emailField.getText().toString());
                                                Admin.setEmail(emailField.getText().toString(), key);
                                                Admin.setPass(pass1Field.getText().toString(), key);
                                                Admin.setName(userField.getText().toString(), key);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.d("Log", "didn't work");
                                            }
                                        });
                                intent = new Intent(getBaseContext(), HomepageMap.class);
                                intent.putExtra("type", "admin");
                                intent.putExtra("key", key);
                                break;

                            case "homeless":
                                Log.d("Log", "Homeless create");
                                FirebaseDatabase.getInstance().getReference().child("homeless").child(key)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                // Get Post object and use the values to update the UI
//                                        Homeless user = dataSnapshot.getValue(Homeless.class);
//                                        if (user == null) {
//                                            return;
//                                        }
                                                Homeless.setEmail(emailField.getText().toString(), key);
                                                Homeless.setPass(pass1Field.getText().toString(), key);
                                                Homeless.setName(userField.getText().toString(), key);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                intent = new Intent(getBaseContext(), Homeless_Registration.class);
                                intent.putExtra("key", key);
                                break;

                            case "worker":
                                Log.d("Log", "Worker");
                                FirebaseDatabase.getInstance().getReference().child("worker").child(key)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                // Get Post object and use the values to update the UI
//                                        Worker user = dataSnapshot.getValue(Worker.class);
//                                        if (user == null) {
//                                            return;
//                                        }
                                                Worker.setEmail(emailField.getText().toString(), key);
                                                Worker.setPass(pass1Field.getText().toString(), key);
                                                Worker.setName(userField.getText().toString(), key);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                intent = new Intent(getBaseContext(), WorkerRegistration.class);
                                intent.putExtra("key", key);
                                break;

                        }
                        startActivity(intent);
                    }
                }
            }
        );
    }


}
