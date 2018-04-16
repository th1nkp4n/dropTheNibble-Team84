package edu.gatech.shelterme.controllers;

import android.app.FragmentTransaction;
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
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.Worker;
import edu.gatech.shelterme.model.WorkerSocial;

public class WorkerRegistration extends AppCompatActivity {
    //Widgets
    private EditText socialSecurity;
    private Button registerButton;
    private Button cancelButton;
    final private FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_registration);

        //Grab the dialog widgets so we can get info for later
        socialSecurity =  findViewById(R.id.social_security_number);
        registerButton =  findViewById(R.id.register_button);
        cancelButton =  findViewById(R.id.cancel_button);
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

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkerSocial social = new WorkerSocial();
                String message = social.correctSocial(socialSecurity.getText().toString());
                if(!message.equals("valid number")) {
                    Log.d("Log", "Please enter your social security number");
                    BadWorkerRegistrationDialogFragment badReg = new BadWorkerRegistrationDialogFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    badReg.show(ft, "maybe");
                } else {
                    Log.d("Log", "correct inputs");
                    String key = (String) getIntent().getSerializableExtra("key");
                    Log.d("Log", socialSecurity.getText().toString());
                    Worker user = new Worker();
                    user.setSocial(socialSecurity.getText().toString(), key);

                    Intent home = new Intent(getBaseContext(), HomepageMap.class);
                    home.putExtra("type", "worker");
                    home.putExtra("key", key);
                    startActivity(home);
                }
            }
        });
    }
}
