package com.example.guest.beerhere.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guest.beerhere.Constants;
import com.example.guest.beerhere.Models.Brewery;
import com.example.guest.beerhere.R;
import com.example.guest.beerhere.adapters.BreweryListAdapter;
import com.example.guest.beerhere.services.BDBService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindBeer extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private String mRecentAddress;

    @Bind(R.id.locationTextView)
    TextView locationText; //  This is the text view at the top of the screen, will be changed once we can make API calls.
    @Bind(R.id.listView)
    ListView beerList;

    public ArrayList<Brewery> mBreweries = new ArrayList<>();

    public static final String TAG = FindBeer.class.getSimpleName();

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private BreweryListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
        ButterKnife.bind(this);

        Intent intent = getIntent();  //  Gets the string from the user from the main activity intent.
        String location = intent.getStringExtra("zip");// creates a new string to put in the message, and use as the argument for find breweries.

        locationText.setText("Here are the closest places in " + location + " to get a beer...");

        findBreweries(location);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        Log.d("Shared Pref Location", mRecentAddress);
    }

    private void findBreweries(String location) {
        final BDBService BDBService = new BDBService();
        BDBService.findBreweries(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mBreweries = BDBService.processResults(response);

                FindBeer.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new BreweryListAdapter(getApplicationContext(), mBreweries);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(FindBeer.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}





