package com.tojo.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

public class ViewSubDomain extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sub_domain);

        //TextView txt = findViewById(R.id.txt1save);

        Intent intent = getIntent();
//        String domainName = intent.getStringExtra("domainName");
//        String fullName = intent.getStringExtra("fullName");
//        String Mode = intent.getStringExtra("Mode");
//        String time = intent.getStringExtra("time");

        Domain domain = getIntent().getParcelableExtra("ADC");
        String x = String.valueOf(domain.getSubDomainList().size());

        //txt.setText(domain.getSubDomainList().get(0).getSubDomainName());

        //linking recyclerview ViewSubDomain...
        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewSubDomain);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));

        adapter=new CustomAdapterSubDomain(domain.getSubDomainList(),getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}