package com.example.guest.beerhere.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.beerhere.Constants;
import com.example.guest.beerhere.Models.Brewery;
import com.example.guest.beerhere.R;
import com.example.guest.beerhere.ui.BreweryDetailActivity;
import com.example.guest.beerhere.util.ItemTouchHelperViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.BreakIterator;
import java.util.ArrayList;

import static com.example.guest.beerhere.R.id.breweryImageView;

/**
 * Created by Guest on 6/11/17.
 */

public class FirebaseBreweryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;


    View mView;
    Context mContext;
    public ImageView mBreweryImageView;

    public FirebaseBreweryViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindBrewery(Brewery brewery) {
        mBreweryImageView = (ImageView) mView.findViewById(R.id.breweryImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.breweryNameTextView);


        Picasso.with(mContext)
                .load(brewery.getLogo())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mBreweryImageView);

        nameTextView.setText(brewery.getName());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Brewery> breweries = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_BREWERIES);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    breweries.add(snapshot.getValue(Brewery.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, BreweryDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("breweries", Parcels.wrap(breweries));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}