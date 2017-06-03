package com.example.guest.beerhere.ui;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by Guest on 5/31/17.
 */

public class RatingAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] Beers;
    private String[] Ratings;

    public RatingAdapter(Context mContext, int resource, String[] Beers, String[] Ratings) {
        super(mContext, resource);
        this.mContext = mContext;
        this.Beers = Beers;
        this.Ratings = Ratings;
    }

    @Override
    public Object getItem(int position) {
        String beer = Beers[position];
        String rating = Ratings[position];
        return String.format("%s \nRating:%s/10", beer, rating);
    }

    @Override
    public int getCount() {
        return Beers.length;
    }
}
