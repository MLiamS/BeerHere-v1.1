package com.example.guest.beerhere.ui;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.guest.beerhere.Constants;
import com.example.guest.beerhere.Models.Brewery;
import com.example.guest.beerhere.R;
import com.example.guest.beerhere.adapters.FirebaseBreweryListAdapter;
import com.example.guest.beerhere.adapters.FirebaseBreweryViewHolder;
import com.example.guest.beerhere.util.OnStartDragListener;
import com.example.guest.beerhere.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedBreweryListActivity extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mBreweryReference;
    private FirebaseBreweryListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_beer);
        ButterKnife.bind(this);
        setUpFirebaseAdapter();
        messages();
        }


    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_BREWERIES)
                .child(uid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        mFirebaseAdapter = new FirebaseBreweryListAdapter(Brewery.class,
                R.layout.brewery_list_item_drag, FirebaseBreweryViewHolder.class,
                query, this, this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    private void messages()
    {
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                Toast.makeText(SavedBreweryListActivity.this, "Rearrange the order of your favorite saved breweries by vertically dragging and dropping their icons on the left.", Toast.LENGTH_SHORT).show();
            }
            public void onFinish() {
                Toast.makeText(SavedBreweryListActivity.this, "Delete breweries by swiping their name horizontally", Toast.LENGTH_LONG).show();
            }
        }.start();

        }

    }
