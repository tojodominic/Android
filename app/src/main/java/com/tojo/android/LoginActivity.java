package com.tojo.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText edemailID,edphone,edpassword;
    Button btnLogin,btnReg;
    FirebaseAuth mAuth;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        edemailID = (EditText) findViewById(R.id.emailIDLogin);
        edphone = (EditText) findViewById(R.id.phoneLogin);
        edpassword = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.loginLogin);
        btnReg = (Button) findViewById(R.id.regLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailID,phone,password;
                emailID = edemailID.getText().toString().trim();
                phone = edphone.getText().toString().trim();
                password = edpassword.getText().toString().trim();
                //Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(),HomeUI.class));

                //checking and granting login...
                if (emailID.isEmpty()){
                    edemailID.setError("email ID is required");
                    edemailID.requestFocus();
                }else if (phone.isEmpty()){
                    edphone.setError("Mobile Number is required");
                    edphone.requestFocus();
                }else if (phone.length()<10){
                    edphone.setError("Invalid Mobile Number");
                    edphone.requestFocus();
                }
                else if (password.isEmpty()){
                    edpassword.setError("Password is required");
                    edpassword.requestFocus();
                }else if (password.length()<8){
                    edpassword.setError("Minimum 8 Characters");
                    edpassword.requestFocus();
                }else{
                    //Toast.makeText(getApplicationContext(), phone, Toast.LENGTH_SHORT).show();
                    //all the needed data is got --> user login...
                    mAuth.signInWithEmailAndPassword(emailID,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                editor=getSharedPreferences("LOGIN",MODE_PRIVATE).edit();
                                editor.putString("emailid",emailID);
                                editor.putString("mobile",phone);
                                editor.commit();
                                startActivity( new Intent(getApplicationContext(),HomeUI.class) );

                                //Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });
    }
}