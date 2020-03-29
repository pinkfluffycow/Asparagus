package com.example.asparagus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewRequestsActivity extends AppCompatActivity
{
    private TextView mLoadingMessage;
    private ProgressBar mProgressBar;
    private TableLayout mTableLayout;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        mLoadingMessage = findViewById(R.id.loadingMessage);
        mProgressBar = findViewById(R.id.progressBar);

        mTableLayout = (TableLayout) findViewById(R.id.requestsTable);

        recyclerView = (RecyclerView) findViewById(R.id.tasksList);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        startLoadData(dataSnapshot);
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
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;
                    case R.id.request:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0,0);
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

    public void startLoadData(DataSnapshot pushes)
    {
        mLoadingMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        loadData(pushes);
    }

    public void loadData(DataSnapshot pushes)
    {
        ArrayList<Request> requests = new ArrayList<>();
        ArrayList<Request> tasks = new ArrayList<>();
        for (DataSnapshot requestSnapshot : pushes.getChildren())
        {
            requests.add(requestSnapshot.getValue(Request.class));
        }

        TableRow trow = new TableRow(this);
        TextView tcol1 = new TextView(this);
          tcol1.setText("     Item Name     ");
          tcol1.setTextSize(18);
          tcol1.setTextColor(Color.BLACK);
          trow.addView(tcol1);
        TextView tcol2 = new TextView(this);
          tcol2.setText("     Quantity     ");
          tcol2.setTextSize(18);
          tcol2.setTextColor(Color.BLACK);
          trow.addView(tcol2);
        TextView tcol3 = new TextView(this);
          tcol3.setText("     Urgency     ");
          tcol3.setTextSize(18);
          tcol3.setTextColor(Color.BLACK);
          trow.addView(tcol3);
        mTableLayout.addView(trow);

        for (int i = 1; i <= requests.size(); i++)
        {
            if(requests.get(i-1).getStatus() != 0)
            {
                tasks.add(requests.get(i-1));
                continue;
            }

            TableRow row = new TableRow(this);
            TextView col1 = new TextView(this);
              col1.setText(requests.get(i - 1).getItemName());
              col1.setTextSize(16);
              col1.setGravity(Gravity.CENTER);
              row.addView(col1);
            TextView col2 = new TextView(this);
              col2.setText(requests.get(i - 1).getQuantity().toString());
              col2.setTextSize(16);
              col2.setGravity(Gravity.CENTER);
              row.addView(col2);
            TextView col3 = new TextView(this);
                switch(requests.get(i - 1).getPriority())
                {
                    case 0:
                        col3.setText("low");
                        break;
                    case 1:
                        col3.setText("medium");
                        break;
                    case 2:
                        col3.setText("high");
                        break;
                    default:
                        col3.setText("low");
                        break;
                }
                col3.setTextSize(16);
                col3.setGravity(Gravity.CENTER);
                row.addView(col3);
            mTableLayout.addView(row);
        }

        // Specify adapter.
        mAdapter = new RequestsListAdapter(tasks);
        recyclerView.setAdapter(mAdapter);

        mLoadingMessage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


}
