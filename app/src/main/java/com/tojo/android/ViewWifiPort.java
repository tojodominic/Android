package com.tojo.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class ViewWifiPort extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wifi_port);

        Intent intent = getIntent();
//        String domainName = intent.getStringExtra("domainName");
//        String fullName = intent.getStringExtra("fullName");
//        String Mode = intent.getStringExtra("Mode");
//        String time = intent.getStringExtra("time");

        WifiData wifiData = getIntent().getParcelableExtra("ADC");

        //txt.setText(domain.getSubDomainList().get(0).getSubDomainName());

        //linking recyclerview ViewSubDomain...
        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewPortList);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));

        //adapter=new CustomAdapterPortList(wifiData.getWifiPortList(),getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}