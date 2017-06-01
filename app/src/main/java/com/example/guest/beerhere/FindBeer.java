package com.example.guest.beerhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindBeer extends AppCompatActivity{
    @Bind(R.id.locationTextView) TextView locationText; //  This is the text view at the top of the screen, will be changed once we can make API calls.
    @Bind(R.id.listView) ListView beerList;

    private String[] Beers = new String[] {"Ale", "Lager",  //  This array is a placeholder for now, not sure if we will query the api by beer type, not sure where this little app is headed just yet
            "Pilsner", "Stout", "Pilsner", "Porter",
            "Bock", "Weissbier", "Lambic", "Kölsch",
            "Malt Liquor"};
    private String[] Ratings = new String[] {"3", "9", "9", "1", "8", "1",
            "7", "8", "4", "10",
            "1"}; //  This array represents a 1 - 10 rating of how much i like the beer style, this should be interactive in the final build.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
        ButterKnife.bind(this);

        RatingAdapter adapter = new RatingAdapter(this, android.R.layout.simple_list_item_activated_1, Beers, Ratings);  // Creates a new arrayadapter named adapter
        beerList.setAdapter(adapter);  // Feeds the beerList listView on the layout the array

        beerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view,int i, long l){
                String Beers = ((TextView) view).getText().toString();
                Toast.makeText(FindBeer.this, Beers, Toast.LENGTH_SHORT).show(); //Item onClick toast, will be changed to something functional in the future;
            }
        });


        Intent intent = getIntent();  //  Gets the string from the user from the main activity intent.
        String location = intent.getStringExtra("zip");// creates a new string to put in the message.
        locationText.setText("Here are the closest places in " + location + " to get a beer...");

    }


}
