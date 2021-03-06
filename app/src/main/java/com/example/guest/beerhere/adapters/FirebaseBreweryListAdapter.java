package com.example.guest.beerhere.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.guest.beerhere.Models.Brewery;
import com.example.guest.beerhere.ui.BreweryDetailActivity;
import com.example.guest.beerhere.ui.FindBeer;
import com.example.guest.beerhere.util.ItemTouchHelperAdapter;
import com.example.guest.beerhere.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Guest on 6/20/17.
 */

public class FirebaseBreweryListAdapter extends FirebaseRecyclerAdapter<Brewery, FirebaseBreweryViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private Context mContext;
    private OnStartDragListener mOnStartDragListener;
    private ChildEventListener mChildEventListener;
    private ArrayList<Brewery> mBreweries = new ArrayList<>();




    public FirebaseBreweryListAdapter(Class<Brewery> modelClass, int modelLayout,
                                         Class<FirebaseBreweryViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mBreweries.add(dataSnapshot.getValue(Brewery.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseBreweryViewHolder viewHolder, Brewery model, int position) {
        viewHolder.bindBrewery(model);

        viewHolder.mBreweryImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BreweryDetailActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("breweries", Parcels.wrap(mBreweries));
                mContext.startActivity(intent);
            }
        });

    }



    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mBreweries, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mBreweries.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
    for (Brewery brewery : mBreweries) {
        int index = mBreweries.indexOf(brewery);
        DatabaseReference ref = getRef(index);
        brewery.setIndex(Integer.toString(index));
        ref.setValue(brewery);
    }
}

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }
}