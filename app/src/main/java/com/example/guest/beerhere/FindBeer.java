package com.example.guest.beerhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindBeer extends AppCompatActivity {
    @Bind(R.id.locationTextView) TextView locationText; //  This is the text view at the top of the screen, will be changed once we can make API calls.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
        ButterKnife.bind(this);

        Intent intent = getIntent();  //  Gets the string from the user from the main activity intent.
        String location = intent.getStringExtra("zip");// creates a new string to put in the message.
        locationText.setText("Here are the closest places in " + location + " to get a beer...");

    }
}
