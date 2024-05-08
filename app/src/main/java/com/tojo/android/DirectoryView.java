package com.tojo.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

public class DirectoryView extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_view);

        SubDomain subDomain = getIntent().getParcelableExtra("DIR");
        String x = String.valueOf(subDomain.directoryList.size());
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();

        //linking recyclerview Open ports...
        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewDirectoryView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));

        adapter=new CustomAdapterDirectory(subDomain.getDirectoryList(),getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}