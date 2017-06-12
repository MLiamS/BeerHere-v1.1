package com.example.guest.beerhere.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.beerhere.Constants;
import com.example.guest.beerhere.R;

import butterknife.Bind;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Bind(R.id.findBeerButton) Button findBeer;
    @Bind(R.id.aboutButton) Button about;
    @Bind(R.id.editTextLocation) EditText editTextLocation;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.bioButton) Button bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface beerFont = Typeface.createFromAsset(getAssets(), "fonts/College Block.otf");
        title.setTypeface(beerFont);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();


        findBeer.setOnClickListener(this);
        about.setOnClickListener(this);
        bio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == findBeer) {  // To the find beer view, which will eventually display nearby beers once api functionality is in place.
            String location = editTextLocation.getText().toString();
            Intent intent = new Intent(MainActivity.this, FindBeer.class);
            addToSharedPreferences(location);
            intent.putExtra("zip", location);
            if (location.length() == 5) {
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a 5 digit zip code", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == about) {  // To the about page view, just a bit about the app.
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        if (v == bio) {  // To the about page view, just a bit about the app.
            Intent intent = new Intent(MainActivity.this, Bio.class);
            startActivity(intent);
        }
    }

        private void addToSharedPreferences(String location) {
            mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
        }

    }






