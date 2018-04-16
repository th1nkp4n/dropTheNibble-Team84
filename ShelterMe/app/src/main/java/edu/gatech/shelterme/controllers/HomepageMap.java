package edu.gatech.shelterme.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import java.util.ArrayList;

import edu.gatech.shelterme.R;
import edu.gatech.shelterme.model.Homeless;
import edu.gatech.shelterme.model.Shelter;

public class HomepageMap extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private DatabaseReference shelterReference;
    private DatabaseReference userReference;
    private ArrayList<Shelter> shelters;
    private ArrayList<String> keys;
    private int[] numClicks;
    private Button checkOut;
    private String type;
    private String key;
    private int shelterID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button logoutButton;
        Button searchButton;

        Log.d("********onCreate*******", "heh");
        type = getIntent().getStringExtra("type");
        key = getIntent().getStringExtra("key");
        shelterID = getIntent().getIntExtra("id", 0);


        shelterReference = FirebaseDatabase.getInstance().getReference()
                .child("shelters");
        shelterReference.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelters = new ArrayList<>();
                keys = new ArrayList<>();

                Log.d("intent", "" + getIntent().getStringExtra("name"));
                if (getIntent().hasExtra("name")) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()){
                        //Log.d("Name Current shelter:", dsp.getValue(Shelter.class).toString());
                        shelters.add(dsp.getValue(Shelter.class));
                        keys.add(dsp.getKey());
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
                } else {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()){
                        //Log.d("CCurrent shelter:", dsp.getValue(Shelter.class).toString());
                        shelters.add(dsp.getValue(Shelter.class));
                        keys.add(dsp.getKey());
                    }
                }
                numClicks = new int[keys.size()];
                setUpMap();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        logoutButton = findViewById(R.id.homepage_logout_button);
        logoutButton.setOnClickListener((view)-> {
                Log.d("Log", "User logged out");
                Intent intent = new Intent(getBaseContext(), LoginPage.class);
                startActivity(intent);
            }
        );

        checkOut = findViewById(R.id.checkOut);
        checkOut.setVisibility(View.INVISIBLE);
        userReference = FirebaseDatabase.getInstance().getReference();

        if (type.equals("homeless")) {
            userReference.child("homeless").child(key)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Homeless user = dataSnapshot.getValue(Homeless.class);
                            if (user == null) {
                                return;
                            }
                            if (user.getCheckedIn() >= 0) {
                                checkOut.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }
        checkOut.setOnClickListener(( v)-> {
                userReference.child("homeless").child(key)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Homeless user = dataSnapshot.getValue(Homeless.class);
                                if (user == null) {
                                    return;
                                }
                                int famIn = user.getFamilies();
                                int indIn = user.getSingles();

                                Log.d("log ind: ", Integer.toString(indIn));
                                Log.d("log fam: ", Integer.toString(famIn));
                                user.setSingles(0, key);
                                user.setFamilies(0, key);
                                user.setCheckedIn(-1, key);
                                Log.d("log :", user.toString());
                                Log.d("log ind: ", Integer.toString(indIn));
                                Log.d("log fam: ", Integer.toString(famIn));

                                shelterReference = FirebaseDatabase.getInstance().getReference()
                                        .child("shelters");
                                shelterReference.child(Integer.toString(shelterID))
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapSnapshot) {
                                                Shelter shelter = snapSnapshot.getValue(Shelter.class);
                                                Log.d("log :", "nested onDataChange");
                                                if (shelter == null) {
                                                    return;
                                                }
                                                int famVac = shelter.getFamilyVacancies();
                                                int indVac = shelter.getSingleVacancies();
                                                Log.d("log ind: ", Integer.toString(indIn));
                                                Log.d("log fam: ", Integer.toString(famIn));
                                                shelter.setSingleVacancies(indVac + indIn, Integer.toString(shelterID));
                                                shelter.setFamilyVacancies(famVac + famIn, Integer.toString(shelterID));
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("log: ", "user part didn't work.");
                            }
                        });
                checkOut.setVisibility(View.INVISIBLE);
            }
        );

        searchButton = findViewById(R.id.search);
        searchButton.setOnClickListener(( view) ->{
                Intent intent = new Intent(getBaseContext(), Search.class);
                intent.putExtra("id", -1);
                intent.putExtra("key", getIntent().getStringExtra("key"));
                intent.putExtra("type", getIntent().getStringExtra("type"));
                startActivity(intent);
            }
        );


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
                LatLng currShelter = new LatLng(latitude, longitude);
                Marker marker = mMap.addMarker(new MarkerOptions().position(currShelter).title( s.getName()).snippet(s.getPhone()));
                marker.setTag(i);
            }
        }
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        if (marker == null) {
            return false;
        }
        int i = (int) marker.getTag();
        numClicks[i] = numClicks[i] + 1;
        if (numClicks[i] > 1) {
            Intent intent = new Intent(getBaseContext(), Shelter_detail_Page.class);
            intent.putExtra("id", Integer.valueOf(keys.get(i)));
            intent.putExtra("type", type);
            intent.putExtra("key", key);
            startActivity(intent);
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


}
