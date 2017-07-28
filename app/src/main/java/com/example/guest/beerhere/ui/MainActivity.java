package com.example.guest.beerhere.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.beerhere.Constants;
import com.example.guest.beerhere.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.guest.beerhere.services.GoogleAPIService;


import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private DatabaseReference mSearchedLocationReference;
    private ValueEventListener mSearchedLocationReferenceListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    protected LocationManager locationManager;
    public final String TAG = this.getClass().getSimpleName();
    private final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private String mLatitude;
    private String mLongitude;
    public String postalCode;





    @Bind(R.id.findBeerButton) Button findBeer;
    @Bind(R.id.aboutButton) Button about;
    @Bind(R.id.editTextLocation) EditText editTextLocation;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.bioButton) Button bio;
    @Bind(R.id.savedBreweriesButton) Button savedBreweriesButton;
    @Bind(R.id.location) TextView locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_ACCESS_COARSE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 100, this); //starts the listener think about moving this
            Toast.makeText(this, "listening...", Toast.LENGTH_SHORT).show();
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mLongitude = location.getLongitude()+"";
            mLatitude = location.getLatitude()+"";

            locationText.setText("Long: " + mLongitude + "    |    " + " Lat: "+ mLatitude);
            Log.d("Long, Lat", mLongitude + ", " + mLatitude);

            getZip(mLatitude, mLongitude);

//            double dLatitude= Double.parseDouble(mLatitude);
//            double dLongitude= Double.parseDouble(mLongitude);
//
//           geocoder.getFromLocation(dLatitude, dLongitude, 1);


        } else {
            Toast.makeText(this, "nope", Toast.LENGTH_LONG).show();
        }

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Happy beer discovery, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };

        Typeface beerFont = Typeface.createFromAsset(getAssets(), "fonts/College Block.otf");
        title.setTypeface(beerFont);



        findBeer.setOnClickListener(this);
        about.setOnClickListener(this);
        bio.setOnClickListener(this);
        savedBreweriesButton.setOnClickListener(this);


        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

        mSearchedLocationReferenceListener = mSearchedLocationReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Location updated", "location: " + location);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == findBeer) {  // To the find beer view, which will eventually display nearby beers once api functionality is in place.
            String location = editTextLocation.getText().toString();

            saveLocationToFirebase(location); //calls the method to save the location zip to firebase.


            Intent intent = new Intent(MainActivity.this, FindBeer.class);
            intent.putExtra("zip", location);
            if (location.length() == 5) {
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a 5 digit zip code", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == about) {  // To the about page view, just a bit about the app.
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        if (v == bio) {  // To the about page view, just a bit about the app.
            Intent intent = new Intent(MainActivity.this, Bio.class);
            startActivity(intent);
        }

        if (v == savedBreweriesButton) {
            Intent intent = new Intent(MainActivity.this, SavedBreweryListActivity.class);
            startActivity(intent);
        }
    }

    public void saveLocationToFirebase(String location) {
        mSearchedLocationReference.push().setValue(location);
    }

    @Override
        protected void onDestroy() {
            super.onDestroy();
            mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
    }
        private void logout() {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude()+"";
        mLongitude = location.getLongitude()+"";
        Log.d(TAG, "onLocationChanged: " + mLatitude + "," + mLongitude);
        locationText.setText("Long: " + mLongitude + "    |    " + " Lat: "+ mLatitude);
        Toast.makeText(this, "moving...", Toast.LENGTH_SHORT).show();
        getZip(mLatitude, mLongitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void getZip(String lat, String lng)    {
        final GoogleAPIService GoogleAPIService = new GoogleAPIService();
        GoogleAPIService.getPostal(lat, lng, new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                postalCode = GoogleAPIService.processResults(response);
                Log.d(postalCode, "run: ");

            }
        });
    }
}








