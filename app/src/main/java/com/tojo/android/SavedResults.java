package com.tojo.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedResults extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DatabaseReference reference;
    List<Domain> domainList;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_results);

        //get the current user from sharedpreference...
        //String login = "9447574692";
        preferences=getSharedPreferences("LOGIN",MODE_PRIVATE);
        String login=preferences.getString("mobile",null);

        //linking recyclerview...
        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewSavedResults);
        recyclerView.setHasFixedSize(true);

        domainList = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        reference= FirebaseDatabase.getInstance().getReference().child("Data").child("ScanResult").child(login);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnap:snapshot.getChildren()){
                    Domain dataClass=dataSnap.getValue(Domain.class);
                    domainList.add(dataClass);
                }

                adapter=new CustomAdapterDomain(domainList,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}