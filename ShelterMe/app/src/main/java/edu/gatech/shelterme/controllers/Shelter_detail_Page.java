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
    private DatabaseReference userReference;
    TextView name;
    TextView address;
    TextView longitude;
    TextView latitude;
    TextView specialnotes;
    TextView famVacancies;
    TextView indVacancies;
    TextView restrictions;
    TextView number;
    Button cancel;
    Button checkIn;
    int shelterID;
    String key;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail__page);
        shelterID =  getIntent().getIntExtra("id", 0);
        key = getIntent().getStringExtra("key");
        type = getIntent().getStringExtra("type");
        name = (TextView) findViewById(R.id.gender);
        famVacancies = (TextView) findViewById(R.id.famVac);
        indVacancies = (TextView) findViewById(R.id.indVac);
        restrictions = (TextView) findViewById(R.id.restrictions);
        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
        address = (TextView) findViewById(R.id.address);
        specialnotes = (TextView) findViewById(R.id.specialnotes);
        number = (TextView) findViewById(R.id.phone);
        cancel = (Button) findViewById(R.id.cancel);
        checkIn = (Button) findViewById(R.id.checkIn);

        checkIn.setVisibility(View.INVISIBLE);

        shelterReference = FirebaseDatabase.getInstance().getReference()
                .child("shelters");
        shelterReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference shelt = shelterReference.orderByChild("name").equalTo(shelterID).getRef();
                shelt.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList mySheltlist = (ArrayList) dataSnapshot.getValue();
                        HashMap<String, Object> myShelt= (HashMap<String, Object>) mySheltlist.get(shelterID);
                        name.setText(name.getText() + myShelt.get("name").toString()) ;
                        famVacancies.setText(famVacancies.getText() + myShelt.get("familyVacancies").toString());
                        indVacancies.setText(indVacancies.getText() + myShelt.get("singleVacancies").toString());
                        restrictions.setText(restrictions.getText() + myShelt.get("restriction").toString());
                        longitude.setText( longitude.getText() + myShelt.get("longitude").toString());
                        latitude.setText(latitude.getText() + myShelt.get("latitude").toString());
                        address.setText(address.getText() + myShelt.get("address").toString());
                        specialnotes.setText(specialnotes.getText() + myShelt.get("specialNotes").toString());
                        number.setText(number.getText() + myShelt.get("phone").toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                //Toast.makeText(PostDetailActivity.this, "Failed to load post.",
                //Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(getBaseContext(), HomepageMap.class);
                start.putExtra("key", key);
                start.putExtra("id", shelterID);
                start.putExtra("type", type);
                startActivity(start);
            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getBaseContext(), CheckInPage.class);
                start.putExtra("key", key);
                start.putExtra("id", shelterID);
                start.putExtra("type", type);
                startActivity(start);
            }
        });

    }
}
