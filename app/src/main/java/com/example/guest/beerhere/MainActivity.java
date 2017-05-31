package com.example.guest.beerhere;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.findBeerButton) Button findBeer;
    @Bind(R.id.aboutButton) Button about;
    @Bind(R.id.editTextLocation) EditText editTextLocation;
    @Bind(R.id.title) TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface beerFont = Typeface.createFromAsset(getAssets(), "fonts/College Block.otf");
        title.setTypeface(beerFont);

        findBeer.setOnClickListener(this);
        about.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if(v == findBeer) {  // To the find beer view, which will eventually display nearby beers once api functionality is in place.
            String location  = editTextLocation.getText().toString();
            Intent intent = new Intent(MainActivity.this, FindBeer.class);
            intent.putExtra("zip", location);
            startActivity(intent);
        }

        if(v == about)  {  // To the about page view, just a bit about the app.
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

    }

}






