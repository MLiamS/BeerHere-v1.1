package com.example.guest.beerhere.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

public class GoogleAPIService {

    public static void getPostal(String lat, String lng, Callback callback)  {
    OkHttpClient client = new OkHttpClient.Builder()
            .build();

    HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GEOAPI_BASE_URL).newBuilder();

    String url = urlBuilder.build().toString() + lat + "," + lng + "&key=" + Constants.GEO_API_KEY;

        Log.d("Api call address", url);

    Request request = new Request.Builder()
            .url(url)
            .build();

    Call call = client.newCall(request);
        call.enqueue(callback);

}
    public String processResults(Response response) {

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject jOb = new JSONObject(jsonData);
                JSONArray dataJSON = jOb.getJSONArray("results");


                    JSONObject locationJSON = dataJSON.getJSONObject(0);

                    Log.d("JSON GOOGLE API!!!!", locationJSON.toString());
//                for c in data.results[0].address_components
//                if c.types[0] == 'postal_code'
//                alert c.short_name
                    Log.d("zip", "XXXXXXXXXXXXXXXXXXXX");
                    String zip = locationJSON.getJSONArray("address_components").toString();
                    Log.d("zip", zip);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
