package com.tojo.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DNSView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnsview);

        SubDomain subDomain = getIntent().getParcelableExtra("DIR");
        String data = subDomain.getDNS();
        TextView txtDNS = findViewById(R.id.dnsDNSView);
        txtDNS.setText(data);
    }
}