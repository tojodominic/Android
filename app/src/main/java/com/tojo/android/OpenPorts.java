package com.tojo.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class OpenPorts extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_ports);

        SubDomain subDomain = getIntent().getParcelableExtra("PORT");
        String x = String.valueOf(subDomain.portList.size());
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();

        //linking recyclerview Open ports...
        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewOpenPorts);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));

        adapter=new CustomAdapterPort(subDomain.getPortList(),getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}