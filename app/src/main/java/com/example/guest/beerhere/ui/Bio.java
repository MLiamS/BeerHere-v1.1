package com.example.guest.beerhere.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.guest.beerhere.Constants;
import com.example.guest.beerhere.R;


public class Bio extends AppCompatActivity implements View.OnClickListener{
    TextView mTextViewBio;
    ImageButton contact;
    ImageButton gitHub;
    ImageButton linkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        mTextViewBio = (TextView) findViewById(R.id.textViewBio1);
        mTextViewBio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)  {
        if (v == mTextViewBio) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.EURL));
            startActivity(webIntent);

        }
    }
}