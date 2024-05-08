package com.tojo.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WhoisView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whois_view);

        SubDomain subDomain = getIntent().getParcelableExtra("DIR");
        String data = subDomain.getWhois();
        TextView txtWhois = findViewById(R.id.whoisWhoisView);
        txtWhois.setText(data);
    }
}