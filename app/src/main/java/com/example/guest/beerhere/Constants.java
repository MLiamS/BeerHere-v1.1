package com.example.guest.beerhere;

import com.example.guest.beerhere.BuildConfig;

/**
 * Created by Guest on 6/1/17.
 */

public class Constants {
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String API_BASE_URL = "http://api.brewerydb.com/v2/locations?";
    public static final String YOUR_QUERY_PARAMETER = "postalCode";
    public static final String API_KEY_QUERY_PARAMETER = "key";
    public static final String PREFERENCES_LOCATION_KEY = "location";
    public static final String FIREBASE_CHILD_SEARCHED_LOCATION = "searchedLocation";
    public static final String FIREBASE_CHILD_BREWERIES = "breweries";
    public static final String EURL = "https://www.epicodus.com/";
    public static final String FIREBASE_QUERY_INDEX = "index";

}