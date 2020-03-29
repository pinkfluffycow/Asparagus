package com.example.asparagus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String EXTRA_MESSAGE = "com.example.asparagus.MESSAGE";

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference(); // initialize database reference

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set request Selected
        bottomNavigationView.setSelectedItemId(R.id.request);

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
                        return true;
                    case R.id.volunteer:
                        startActivity(new Intent(getApplicationContext()
                                , ProvideHelpActivity.class));
                        overridePendingTransition(0,0);
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

    /** Called when the user taps the Send button */
    public void submitItemRequest(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText itemNameInput = (EditText) findViewById(R.id.itemNameInput);
        EditText quantityInput = (EditText) findViewById(R.id.quantityInput);
        SeekBar priorityInput = (SeekBar) findViewById(R.id.priorityInput);

        Request request = new Request();
        request.setItemName(itemNameInput.getText().toString());
        request.setQuantity(Integer.parseInt(quantityInput.getText().toString()));
        request.setPriority(priorityInput.getProgress());
        request.setStatus(0);
        mDatabase.push().setValue(request);

        String message = "Submitted request for " + quantityInput.getText().toString() + " " + itemNameInput.getText().toString() + " with priority " + priorityInput.getProgress() + ".";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
