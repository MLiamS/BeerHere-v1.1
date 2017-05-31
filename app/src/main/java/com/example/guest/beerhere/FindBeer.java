package com.example.guest.beerhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, Beers);  // Creates a new arrayadapter named adapter
        beerList.setAdapter(adapter);  // Feeds the beerList listView on the layout the array

        beerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view,int i, long l){
                String Beers = ((TextView) view).getText().toString();
                Toast.makeText(FindBeer.this, Beers, Toast.LENGTH_SHORT).show(); //Item on click toast, will be changed to something functional in the future.
            }
        });


        Intent intent = getIntent();  //  Gets the string from the user from the main activity intent.
        String location = intent.getStringExtra("zip");// creates a new string to put in the message.
        locationText.setText("Here are the closest places in " + location + " to get a beer...");

    }


}
