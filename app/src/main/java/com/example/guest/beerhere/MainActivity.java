package com.example.guest.beerhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.findBeerButton) Button findBeer;
    @Bind(R.id.aboutButton) Button about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    public void onClick(View v) {
        if(v == about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }
    }

}
