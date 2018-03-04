package edu.gatech.shelterme.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Shelter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail__page);
        final String shelterName = (String) getIntent().getStringExtra("name");
        name = (TextView) findViewById(R.id.name);
        capacity = (TextView) findViewById(R.id.capacity);
        restrictions = (TextView) findViewById(R.id.restrictions);
        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
        address = (TextView) findViewById(R.id.address);
        specialnotes = (TextView) findViewById(R.id.specialnotes);
        number = (TextView) findViewById(R.id.phone);
        shelterReference = FirebaseDatabase.getInstance().getReference()
                .child("shelters");
        shelterReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference shelt = shelterReference.orderByChild("name").equalTo(shelterName).getRef();
                name.setText(shelt.child("name").toString());

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

    }
}
