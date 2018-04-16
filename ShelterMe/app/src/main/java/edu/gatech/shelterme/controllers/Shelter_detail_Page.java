package edu.gatech.shelterme.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Homeless;

public class Shelter_detail_Page extends AppCompatActivity {
    private DatabaseReference shelterReference;
    private TextView name;
    private TextView address;
    private TextView longitude;
    private TextView latitude;
    private TextView specialNotes;
    private TextView famVacancies;
    private TextView indVacancies;
    private TextView restrictions;
    private TextView number;
    private Button checkIn;
    private int shelterID;
    private String key;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail__page);

        DatabaseReference userReference;
        shelterID =  getIntent().getIntExtra("id", 0);
        key = getIntent().getStringExtra("key");
        type = getIntent().getStringExtra("type");
        name =  findViewById(R.id.gender);
        famVacancies =  findViewById(R.id.famVac);
        indVacancies = findViewById(R.id.indVac);
        restrictions = findViewById(R.id.restrictions);
        longitude =  findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        address = findViewById(R.id.address);
        specialNotes =  findViewById(R.id.special_notes);
        number = findViewById(R.id.phone);
        Button cancel = findViewById(R.id.cancel);
        checkIn = findViewById(R.id.checkIn);

        checkIn.setVisibility(View.INVISIBLE);

        shelterReference = FirebaseDatabase.getInstance().getReference()
                .child("shelters");
        shelterReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference shelter = shelterReference.orderByChild("name").equalTo(shelterID).getRef();
                shelter.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList myShelterList = (ArrayList) dataSnapshot.getValue();
                        if (myShelterList == null) {
                            return;
                        }
                        HashMap<?, ?> myShelter= (HashMap<?, ?>) myShelterList.get(shelterID);
                        name.setText(name.getText() + myShelter.get("name").toString()) ;
                        famVacancies.setText(famVacancies.getText() + myShelter.get("familyVacancies").toString());
                        indVacancies.setText(indVacancies.getText() + myShelter.get("singleVacancies").toString());
                        restrictions.setText(restrictions.getText() + myShelter.get("restriction").toString());
                        longitude.setText( longitude.getText() + myShelter.get("longitude").toString());
                        latitude.setText(latitude.getText() + myShelter.get("latitude").toString());
                        address.setText(address.getText() + myShelter.get("address").toString());
                        specialNotes.setText(specialNotes.getText() + myShelter.get("specialNotes").toString());
                        number.setText(number.getText() + myShelter.get("phone").toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        if (type.equals("homeless")) {
            userReference = FirebaseDatabase.getInstance().getReference();
            Log.d("Key: ", "the key in details is " + key);
            userReference.child("homeless").child(key)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Homeless user = dataSnapshot.getValue(Homeless.class);
                            if (user == null) {
                                return;
                            }
                            if (user.getCheckedIn() == -1) {
                                checkIn.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("log: ", "check in visibility didn't work");
                        }
                    });
        }

        cancel.setOnClickListener(( view)-> {
                Intent start = new Intent(getBaseContext(), HomepageMap.class);
                start.putExtra("key", key);
                start.putExtra("id", shelterID);
                start.putExtra("type", type);
                startActivity(start);
            }
        );

        checkIn.setOnClickListener(( v) ->{
                Intent start = new Intent(getBaseContext(), CheckInPage.class);
                start.putExtra("key", key);
                start.putExtra("id", shelterID);
                start.putExtra("type", type);
                startActivity(start);
            }
        );

    }
}
