package com.tojo.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordStrength extends AppCompatActivity {

    EditText edInputPassword;
    Button btnCheck;
    TextView txtOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_strength);

        edInputPassword = (EditText) findViewById(R.id.passwordInput);
        txtOutput = (TextView) findViewById(R.id.passwordOutput);
        btnCheck = (Button) findViewById(R.id.passwordCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edInputPassword.getText().toString().trim();

                int score = PasswordStrengthAnalyzer.analyzePasswordStrength(password);
                txtOutput.setText("Password strength: " + score + "/100");
            }
        });

    }
}