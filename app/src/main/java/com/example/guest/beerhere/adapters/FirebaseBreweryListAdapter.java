package com.example.guest.beerhere.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.guest.beerhere.Models.Brewery;
import com.example.guest.beerhere.util.ItemTouchHelperAdapter;
import com.example.guest.beerhere.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Guest on 6/20/17.
 */

public class FirebaseBreweryListAdapter extends FirebaseRecyclerAdapter<Brewery, FirebaseBreweryViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private Context mContext;
    private OnStartDragListener mOnStartDragListener;


    public FirebaseBreweryListAdapter(Class<Brewery> modelClass, int modelLayout,
                                         Class<FirebaseBreweryViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
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
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}