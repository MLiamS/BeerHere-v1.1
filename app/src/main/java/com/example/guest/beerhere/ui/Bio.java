package com.example.guest.beerhere.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.media.session.IMediaControllerCallback;
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


public class Bio extends AppCompatActivity implements View.OnClickListener {
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
        contact = (ImageButton) findViewById(R.id.emailButton);
        contact.setOnClickListener(this);
        gitHub = (ImageButton) findViewById(R.id.githubButton);
        gitHub.setOnClickListener(this);
        linkedin = (ImageButton) findViewById(R.id.linkedinButton);
        linkedin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mTextViewBio) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.EURL));
            startActivity(webIntent);
        }
        if (v == gitHub) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/mliams"));
            startActivity(webIntent);
        }
        if (v == linkedin) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/liam-stabeno-6a647613b"));
            startActivity(webIntent);
        }
        if (v == contact) {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:liamstabeno@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Beer Here!");
            startActivity(emailIntent);

        }
    }
}