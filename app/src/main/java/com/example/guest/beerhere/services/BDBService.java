package com.example.guest.beerhere.services;


import android.util.Log;

import com.example.guest.beerhere.Constants;
import com.example.guest.beerhere.Models.Brewery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 6/1/17.
 */

public class BDBService {

    public static void findBreweries(String location, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.API_KEY_QUERY_PARAMETER, Constants.API_KEY);
        urlBuilder.addQueryParameter(Constants.YOUR_QUERY_PARAMETER, location);


        String url = urlBuilder.build().toString();

        Log.d("Api call address", url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }
        public ArrayList<Brewery> processResults(Response response) {
            ArrayList<Brewery> breweries = new ArrayList<>();

            try {
                String jsonData = response.body().string();
                if (response.isSuccessful()) {
                    System.out.println("Before new JSONObject");
                    JSONObject fuck = new JSONObject(jsonData);
                    System.out.println("After new JSONObject");
                    JSONArray dataJSON = fuck.getJSONArray("data");

                    for (int i = 0; i < dataJSON.length(); i++) {
                        JSONObject breweryJSON = dataJSON.getJSONObject(i);
                        String name = breweryJSON.getJSONObject("brewery").getString("name");
                        System.out.println(name);
                        String phone = breweryJSON.optString("phone", "Phone not available");
                        String website = breweryJSON.getJSONObject("brewery").getString("website");
                        String address = breweryJSON.getString("streetAddress");
                        double latitude = breweryJSON.getDouble("latitude");
                        double longitude = breweryJSON.getDouble("longitude");
                        String icon = breweryJSON.getJSONObject("brewery").getJSONObject("images").getString("icon");
                        String logo = breweryJSON.getJSONObject("brewery").getJSONObject("images").getString("squareLarge");
                        String city = breweryJSON.getString("locality");
                        String state = breweryJSON.getString("region");
                        String closed = breweryJSON.getString("isClosed");
                        Brewery brewery = new Brewery(name, phone, website, address, latitude, longitude, icon, logo, city, state, closed);
                        breweries.add(brewery);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return breweries;
        }
}
