package com.example.asparagus;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProvideHelpActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference mDatabase;
    private ArrayList<Request> requests = new ArrayList<>();
    private ArrayList<LatLng> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_help);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        recyclerView = (RecyclerView) findViewById(R.id.requestsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        locations.add(new LatLng(34.071200, -118.451690));   // Courtside
        locations.add(new LatLng(34.0692791, -118.4458268)); // Luskin Conference Center
        locations.add(new LatLng(34.0727586, -118.4421988)); // Royce Hall
        locations.add(new LatLng(34.0685754, -118.4429503)); // Boelter Hall
        locations.add(new LatLng(34.0717594, -118.4515682)); // Feast
        locations.add(new LatLng(34.069822, -118.450087));   // Dykstra Hall
        locations.add(new LatLng(34.073519, -118.451774));   // The Study

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        loadData(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                }
        );


        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set request Selected
        bottomNavigationView.setSelectedItemId(R.id.volunteer);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext()
                                , ViewRequestsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.request:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.volunteer:
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext()
                                , ProgressActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void loadData(DataSnapshot pushes)
    {
        // Fetch requests.
        for (DataSnapshot requestSnapshot : pushes.getChildren())
        {
            requests.add(requestSnapshot.getValue(Request.class));
        }

        // Specify adapter.
        mAdapter = new RequestsListAdapter(requests);
        recyclerView.setAdapter(mAdapter);

        // Mark request locations on map.
        for (int i = 0; i < requests.size(); i++)
        {
            mMap.addMarker(new MarkerOptions().position(locations.get(i % locations.size())).title("a"));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at UCLA and move the camera.
        LatLng ucla = new LatLng(34.068920, -118.445183);
        mMap.addMarker(new MarkerOptions().position(ucla).title("UCLA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucla, 15));
    }
}