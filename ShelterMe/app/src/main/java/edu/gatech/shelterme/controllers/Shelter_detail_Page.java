package edu.gatech.shelterme.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Shelter_detail_Page extends AppCompatActivity {
    private DatabaseReference shelterReference;
    TextView name;
    TextView address;
    TextView longitude;
    TextView latitude;
    TextView specialnotes;
    TextView capacity;
    TextView restrictions;
    TextView number;
    Button cancel;
    Button checkIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail__page);
        final int shelterID = (int) getIntent().getIntExtra("id",0);
        name = (TextView) findViewById(R.id.gender);
        capacity = (TextView) findViewById(R.id.capacity);
        restrictions = (TextView) findViewById(R.id.restrictions);
        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
        address = (TextView) findViewById(R.id.address);
        specialnotes = (TextView) findViewById(R.id.specialnotes);
        number = (TextView) findViewById(R.id.phone);
        cancel = (Button) findViewById(R.id.cancel);
        checkIn = (Button) findViewById(R.id.checkIn);

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
                        capacity.setText(capacity.getText() + myShelt.get("capacity").toString());
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(getBaseContext(), HomepageMap.class);
                startActivity(start);
            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(getBaseContext(), CheckInPage.class);
                startActivity(start);
            }
        });

    }
}
