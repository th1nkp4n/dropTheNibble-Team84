package edu.gatech.shelterme.controllers;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Admin;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.User;
import edu.gatech.shelterme.model.Worker;

import static java.util.logging.Logger.global;

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
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);



        //Grab the dialog widgets so we can get info for later
        userField = (EditText) findViewById(R.id.loginpage_username_input);
        passField = (EditText) findViewById(R.id.loginpage_password_input);
        loginButton = (Button) findViewById(R.id.homepage_logout_button);
        registerButton = (Button) findViewById(R.id.loginpage_register_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences("Prefs", 0);
                String pass = passField.getText().toString();
                String email = userField.getText().toString();
                User data = new User();
                data.setData(false);
                Log.d("Log", "login button clicked");


                if (pass == null) {
                    //tell them they had the wrong username or password
                    Log.d("Log", "incorrect");
                    BadLoginAlertDialogFragment badLogin = new BadLoginAlertDialogFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    badLogin.show(ft, "maybe");
                } else {
                    DatabaseReference allPassMatchesH = ref.child("homeless").orderByChild("email").equalTo(email).getRef();
                    DatabaseReference allPassMatchesA = ref.child("admin").orderByChild("email").equalTo(email).getRef();
                    DatabaseReference allPassMatchesW = ref.child("worker").orderByChild("email").equalTo(email).getRef();
                    Intent intent = new Intent(getBaseContext(), HomepageMap.class);

                    allPassMatchesH.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("Log", "in homeless event listener");
                            for (DataSnapshot homelessSnapshot : dataSnapshot.getChildren()) {
                                Homeless user = (Homeless) homelessSnapshot.getValue(Homeless.class);
                                if (user != null) {
                                    Log.d("Log", user.getName() + "line 82 in homeless");
                                    if (user.getPass().equals(pass) && user.getEmail().equals(email)) {
                                        String key = homelessSnapshot.getKey();
                                        Log.d("Log", "correct inputs");
                                        intent.putExtra("key", key);
                                        intent.putExtra("type", "homeless");
                                        Log.d("Log", "We made it.");
                                        data.setData(true);
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
                                Admin user = (Admin) adminSnapshot.getValue(Admin.class);
                                Log.d("Log", user.getEmail());
                                Log.d("Log", user.getPass());
                                Log.d("Log", email);
                                Log.d("Log", pass);
                                Log.d("Log", "" + user.getEmail().equals(email));
                                if (user != null && user.getPass().equals(pass) && user.getEmail().equals(email)) {
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
                            Intent intent = null;
                            for (DataSnapshot workerSnapshot: dataSnapshot.getChildren()) {
                                Worker user = (Worker) workerSnapshot.getValue(Worker.class);
                                Log.d("Log", user.getEmail());
                                Log.d("Log", user.getPass());
                                if (user != null && user.getPass().equals(pass) && user.getEmail().equals(email)) {
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
                    startActivity(intent);

                }

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Log", "register button on login page pressed");
                Intent intent = new Intent(getBaseContext(), RegistrationRolePage.class);
                startActivity(intent);
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

