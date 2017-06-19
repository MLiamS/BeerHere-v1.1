package com.example.guest.beerhere.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.guest.beerhere.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.button) Button bio;
    @Bind(R.id.button2) Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        bio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == bio) {
            Intent intent = new Intent(AboutActivity.this, Bio.class);
            startActivity(intent);
        }
        if (v == back) {
            Intent intent = new Intent(AboutActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
