package com.example.guest.beerhere.Models;


import org.parceler.Parcel;

/**
 * Created by Guest on 6/1/17.
 */

@Parcel
public class Brewery {
     String name;
     String phone;
     String website;
     String address;
     double latitude;
     double longitude;
     String icon;
     String logo;
     String city;
     String state;
     String closed;
    private String pushId;

    public Brewery() {}

    public Brewery(String name, String phone, String website,
                   String address, double latitude, double longitude,
                   String icon, String logo, String city, String state, String closed)  {

        this.name = name;
        this.phone = phone;
        this.website = website;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.icon = icon;
        this.logo = logo;
        this.city = city;
        this.state = state;
        this.closed = closed;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return  website;
    }

    public String getAddress() {
        return (address + ", " + city + ", " + state); //  change the format of this later.
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getIcon() {
        return icon;
    }

    public String getLogo() {
        return logo;
    }

    public String getClosed() {
        return closed;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }


}
