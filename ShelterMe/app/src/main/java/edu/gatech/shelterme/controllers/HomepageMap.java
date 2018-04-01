package edu.gatech.shelterme.controllers;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Shelter;

public class HomepageMap extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Button logoutButton;
    private DatabaseReference shelterReference;
    //private ListView listView;
    private ArrayAdapter<String> adapter;
    protected ArrayList<Shelter> shelters;
    protected ArrayList<String> keys;
    protected String[] shelterName;
    private Button searchButton;
    //protected ArrayList<String> shelterName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //listView = (ListView) findViewById(R.id.shelterList);
        Log.d("********ONCREATE*******", "hehehe");



        shelterReference = FirebaseDatabase.getInstance().getReference()
                .child("shelters");
        shelterReference.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int length = (int) dataSnapshot.getChildrenCount();
                shelters = new ArrayList<>();
                keys = new ArrayList<>();
                int counter = 0;

                Log.d("intent", "" + getIntent().getStringExtra("name"));
                if (getIntent().hasExtra("name")) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()){
                        Log.d("Name Current shelter:", dsp.getValue(Shelter.class).toString());
                        //Log.d("CCurrent type:", dsp.getValue(Shelter.class).getClass().toString());
                        shelters.add(dsp.getValue(Shelter.class));
                        keys.add(dsp.getKey());
                        //shelterName.add(dsp.getValue(Shelter.class).getName());
                    }

                    if (getIntent().getStringExtra("name").compareTo("Any Shelter") != 0) {
                        String name = getIntent().getStringExtra("name");
                        Log.d("name total size", "" + shelters.size());
                        for (int i = 0; i < shelters.size(); i++) {
                            if (shelters.get(i).getName().compareTo(name) != 0) {
                                shelters.remove(i);
                                keys.remove(i);
                                Log.d("i", "" + i);
                                i--;
                            } else {
                                Log.d("name", shelters.get(i).getName());
                            }
                        }

                        Log.d("filtering by: ", name);
                        Log.d("name size: ", "" + shelters.size());
                    }
                    if (getIntent().getStringExtra("age").compareTo("Any Age") != 0) {
                        String age = getIntent().getStringExtra("age");
                        for (int i = 0; i < shelters.size(); i++) {
                            if (!shelters.get(i).getRestriction().contains(age)) {
                                Log.d("restriction", shelters.get(i).getRestriction());
                                shelters.remove(i);
                                keys.remove(i);
                                i--;
                            }
                        }
                        Log.d("age size: ", "" + shelters.size());
                    }
                    if (getIntent().getStringExtra("gender").compareTo("Any Gender") != 0) {
                        String gender = getIntent().getStringExtra("gender");
                        for (int i = 0; i < shelters.size(); i++) {
                            if (!shelters.get(i).getRestriction().contains(gender)) {
                                shelters.remove(i);
                                keys.remove(i);
                                i--;
                            }
                        }
                        Log.d("gender size: ", "" + shelters.size());
                    }

                    shelterName = new String[shelters.size()];
                    for (int i = 0; i < shelters.size(); i++) {
                        Log.d("shelter: ", shelters.get(i).getName().toString());
                        shelterName[i] = shelters.get(i).getName();
                    }
                } else {
                    shelterName = new String[length];
                    for (DataSnapshot dsp : dataSnapshot.getChildren()){
                        Log.d("CCurrent shelter:", dsp.getValue(Shelter.class).toString());
                        shelters.add(dsp.getValue(Shelter.class));
                        keys.add(dsp.getKey());
                        //Log.d("CCurrent type:", dsp.getValue(Shelter.class).getClass().toString());
                        shelterName[counter++] = dsp.getValue(Shelter.class).toString();
                        //shelterName.add(dsp.getValue(Shelter.class).getName());
                    }
                }
                setUpMap();
//                Log.d("one:", Boolean.toString(listView.isOpaque()));
////                listView.setAdapter(new ArrayAdapter<String>(HomepageMap.this,
////                        android.R.layout.simple_list_item_1, shelterName));
//                Log.d("two:", Boolean.toString(listView.isOpaque()));
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

//        listView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String sheltername = String.valueOf(parent.getItemAtPosition(position));
//                        Intent intent = new Intent(getBaseContext(), Shelter_detail_Page.class);
//                        intent.putExtra("id", keys.get(position));
//                        startActivity(intent);
//                    }
//                }
//        );


        logoutButton = (Button) findViewById(R.id.homepage_logout_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Log", "User logged out");
                Intent intent = new Intent(getBaseContext(), LoginPage.class);
                startActivity(intent);
            }
        });


        searchButton = (Button) findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Search.class);
                startActivity(intent);
            }
        });


    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng atl = new LatLng(33.749, -84.388);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(atl));
        mMap.setMinZoomPreference(10);


    }

    private void setUpMap() {
        if (shelters != null) {
            for (int i = 0; i < shelters.size(); i++) {
                Shelter s = shelters.get(i);
                double latitude = s.getLatitude();
                double longitude = s.getLongitude();
                LatLng currShelt = new LatLng(latitude, longitude);
                Marker marker = mMap.addMarker(new MarkerOptions().position(currShelt).title(s.getName()));
                marker.setTag(keys.get(i));
            }
        }
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        String key = (String) marker.getTag();
        Intent intent = new Intent(getBaseContext(), Shelter_detail_Page.class);
        intent.putExtra("id", Integer.valueOf(key));
        startActivity(intent);


        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


}
