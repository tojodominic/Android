package com.tojo.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Privacy extends AppCompatActivity {

    CheckBox privacyCheckbox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        privacyCheckbox = findViewById(R.id.privacyCheckbox);

        // Handle checkbox state change
        privacyCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Enable/disable button based on checkbox state
                if (isChecked) {
                    // Checkbox is checked
                    // Proceed with your action here
                    // For example, enable the button
                    // button.setEnabled(true);
                    editor=getSharedPreferences("LOGIN",MODE_PRIVATE).edit();
                    editor.putString("Privacy","Checked");
                    editor.commit();


                    preferences=getSharedPreferences("LOGIN",MODE_PRIVATE);
                    String phone=preferences.getString("mobile",null);

                    if(phone!=null){
                        startActivity(new Intent(getApplicationContext(),HomeUI.class));
                        finish();
                    }else{
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }

                } else {
                    // Checkbox is unchecked
                    // For example, disable the button
                    // button.setEnabled(false);
                }
            }
        });
    }
}