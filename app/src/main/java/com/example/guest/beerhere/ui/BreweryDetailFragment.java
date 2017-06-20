package com.example.guest.beerhere.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.beerhere.Constants;
import com.example.guest.beerhere.Models.Brewery;
import com.example.guest.beerhere.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BreweryDetailFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.breweryImageView) ImageView mImageLabel;
    @Bind(R.id.breweryNameTextView) TextView mNameLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.websiteTextView) TextView mWebsite;
    @Bind(R.id.saveTextView) TextView mSaveRestaurantButton;



    private Brewery mBrewery;

    public static BreweryDetailFragment newInstance(Brewery brewery) {
        BreweryDetailFragment breweryDetailFragment = new BreweryDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("brewery", Parcels.wrap(brewery));
        breweryDetailFragment.setArguments(args);
        return breweryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBrewery = Parcels.unwrap(getArguments().getParcelable("brewery"));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brewery_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mBrewery.getLogo()).into(mImageLabel);

        mNameLabel.setText(mBrewery.getName());
        mPhoneLabel.setText(mBrewery.getPhone());
        mAddressLabel.setText(mBrewery.getAddress());
        mWebsite.setText(mBrewery.getWebsite());

        mWebsite.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        mSaveRestaurantButton.setOnClickListener(this);


        return view;

    }

        @Override
        public void onClick(View v) {
            if (v == mWebsite) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mBrewery.getWebsite()));
                startActivity(webIntent);
            }
            if (v == mPhoneLabel) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + mBrewery.getPhone()));
                startActivity(phoneIntent);
            }
            if (v == mAddressLabel) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:" + mBrewery.getLatitude()
                                + "," + mBrewery.getLongitude()
                                + "?q=(" + mBrewery.getName() + ")"));
                startActivity(mapIntent);
            }
            if (v == mSaveRestaurantButton) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                DatabaseReference restaurantRef = FirebaseDatabase
                        .getInstance()
                        .getReference(Constants.FIREBASE_CHILD_BREWERIES)
                        .child(uid);

                DatabaseReference pushRef = restaurantRef.push();
                String pushId = pushRef.getKey();
                mBrewery.setPushId(pushId);
                pushRef.setValue(mBrewery);

                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        }
    }