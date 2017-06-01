package com.example.guest.beerhere;


/**
 * Created by Guest on 6/1/17.
 */

public class Brewery {
        private String mName;
        private String mPhone;
        private String mWebsite;
        private String mAddress;
        private double mLatitude;
        private double mLongitude;
        private String mIcon;
        private String mLogo;
        private String mCity;
        private String mState;
        private Boolean mClosed;

        public Brewery(String name, String phone, String website,
                       String address, double latitude, double longitude,
                       String icon, String logo, String city, String state, Boolean closed)  {

            this.mName = name;
            this.mPhone = phone;
            this.mWebsite = website;
            this.mAddress = address;
            this.mLatitude = latitude;
            this.mLongitude = longitude;
            this.mIcon = icon;
            this.mLogo = logo;
            this.mCity = city;
            this.mState = state;
            this.mClosed = closed;
        }

        public String getName() {
            return mName;
        }

        public String getPhone() {
            return mPhone;
        }

        public String getWebsite() {
            return  mWebsite;
        }

        public String getAddress() {
            return (mAddress + ", " + mCity + ", " + mState); //  change the format of this later.
        }

        public double getLatitude() {
            return mLatitude;
        }

        public double getLongitude() {
            return mLongitude;
        }

        public String getIcon() {
            return mIcon;
        }

        public String getLogo() {
            return mLogo;
        }

        public Boolean getOpen() {
            return !mClosed;
        }

}
