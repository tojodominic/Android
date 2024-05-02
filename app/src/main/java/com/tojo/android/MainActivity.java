package com.tojo.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH=3000;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (!Python.isStarted()){
//            Python.start(new AndroidPlatform(this));
//        }
//
//        Python py = Python.getInstance();
//        String host = "duk.ac.in";
//        PyObject pyObject = py.getModule("main").callAttr("whoisEnum",host);
//
//        Toast.makeText(getApplicationContext(),pyObject.toString(),Toast.LENGTH_LONG).show();


        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences=getSharedPreferences("LOGIN",MODE_PRIVATE);
                String phone=preferences.getString("mobile",null);

                if(phone!=null){
                    startActivity(new Intent(getApplicationContext(),HomeUI.class));
                    finish();
                }else{
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }

            }
        },SPLASH);

    }
}