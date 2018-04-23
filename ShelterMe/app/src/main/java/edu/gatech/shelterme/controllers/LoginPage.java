package edu.gatech.shelterme.controllers;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

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

public class LoginPage extends AppCompatActivity {
    /* ************************
            Widgets we will need for binding and getting information
         */
    private EditText userField;
    private EditText passField;
    final private FirebaseDatabase database = FirebaseDatabase.getInstance();
    final private DatabaseReference ref = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Button loginButton;
        Button registerButton;

        //Grab the dialog widgets so we can get info for later
        userField =  findViewById(R.id.login_page_username_input);
        passField =  findViewById(R.id.login_page_password_input);
        loginButton = findViewById(R.id.homepage_logout_button);
        registerButton =  findViewById(R.id.login_page_register_button);


        loginButton.setOnClickListener(( view)-> {
                //SharedPreferences settings = getSharedPreferences("Prefs", 0);
                String pass = passField.getText().toString();
                String email = userField.getText().toString();
                User data = new User();
                data.setData(false);
                Log.d("Log", "login button clicked");


                if ("".equals(pass)) {
                    //tell them they had the wrong username or password
                    Log.d("Log", "incorrect");
                    BadLoginAlertDialogFragment badLogin = new BadLoginAlertDialogFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    badLogin.show(ft, "maybe");
                } else {

                    Log.d("ayee", "lmao in else");
                    DatabaseReference allPassMatchesH = ref.child("homeless").orderByChild("email").equalTo(email).getRef();
                    DatabaseReference allPassMatchesA = ref.child("admin").orderByChild("email").equalTo(email).getRef();
                    DatabaseReference allPassMatchesW = ref.child("worker").orderByChild("email").equalTo(email).getRef();
                    Intent intent = new Intent(getBaseContext(), HomepageMap.class);

                    allPassMatchesH.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("Log", "in homeless event listener");
                            for (DataSnapshot homelessSnapshot : dataSnapshot.getChildren()) {
                                Homeless user = homelessSnapshot.getValue(Homeless.class);
                                if (user != null) {
                                    Log.d("Log", user.getName() + "line 77 in homeless");
                                    if (user.getPass().equals(pass) && user.getEmail().equals(email)) {
                                        String key = homelessSnapshot.getKey();
                                        Log.d("Log", "correct inputs");
                                        intent.putExtra("key", key);
                                        intent.putExtra("type", "homeless");
                                        Log.d("Log", "We made it.");
                                        data.setData(true);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("Log", "in canceled");
                        }
                    });

                    allPassMatchesA.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("Log", "in admin event listener");
                            for (DataSnapshot adminSnapshot: dataSnapshot.getChildren()) {
                                Admin user = adminSnapshot.getValue(Admin.class);
                                if (user == null) {
                                    return;
                                }
                                Log.d("Log", user.getEmail());
                                Log.d("Log", user.getPass());
                                Log.d("Log", email);
                                Log.d("Log", pass);
                                Log.d("Log", "" + user.getEmail().equals(email));
                                if (user.getPass().equals(pass) && user.getEmail().equals(email)) {
                                    String key = adminSnapshot.getKey();

                                    Log.d("Log", "correct inputs");
                                    intent.putExtra("key", key);
                                    intent.putExtra("type", "admin");
                                    data.setData(true);
                                    startActivity(intent);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    allPassMatchesW.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("Log", "in worker event listener");
                            Intent intent;
                            for (DataSnapshot workerSnapshot: dataSnapshot.getChildren()) {
                                Worker user = workerSnapshot.getValue(Worker.class);
                                if (user == null) {
                                    return;
                                }
                                Log.d("Log", user.getEmail());
                                Log.d("Log", user.getPass());
                                if (user.getPass().equals(pass) && user.getEmail().equals(email)) {
                                    String key = workerSnapshot.getKey();
                                    Log.d("Log", "correct inputs");
                                    intent = new Intent(getBaseContext(), HomepageMap.class);
                                    intent.putExtra("key", key);
                                    intent.putExtra("type", "worker");
                                    data.setData(true);
                                    startActivity(intent);
                                }
                            }

                            //tell them they had the wrong username or password
                            Log.d("Log", "" + data.getData());
                            if (!data.getData()) {
                                Log.d("Log", "incorrect inputs");
                                BadLoginAlertDialogFragment badLogin = new BadLoginAlertDialogFragment();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                badLogin.show(ft, "maybe");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        );
        registerButton.setOnClickListener((view)-> {
                Log.d("Log", "register button on login page pressed");
                Intent intent = new Intent(getBaseContext(), RegistrationRolePage.class);
                startActivity(intent);
            }
        );
    }
}

