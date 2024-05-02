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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button btnLogin,btnRegister;
    EditText edusername,edemail,edphone,edpassword,edrepassword;

    User user;
    DatabaseReference reference;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnLogin = (Button) findViewById(R.id.loginSignUp);
        btnRegister = (Button) findViewById(R.id.regSignUp);
        edusername = (EditText) findViewById(R.id.usernameSignUp);
        edemail = (EditText) findViewById(R.id.emailSignUp);
        edpassword = (EditText) findViewById(R.id.passwordSignUp);
        edphone = (EditText) findViewById(R.id.phoneSignUp);
        edrepassword = (EditText) findViewById(R.id.repasswordSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName=edusername.getText().toString().trim();
                String email=edemail.getText().toString().trim();
                String password=edpassword.getText().toString().trim();
                String phone=edphone.getText().toString().trim();
                String repassword=edrepassword.getText().toString().trim();

                user = new User();

                if (email.isEmpty())
                {
                    edemail.setError("email is required");
                    edemail.requestFocus();
                }else if (password.isEmpty())
                {
                    edpassword.setError("password is required");
                    edpassword.requestFocus();
                }else if (password.length()<8){
                    edpassword.setError("Minimum 8 Characters");
                    edpassword.requestFocus();
                }
                else if (phone.isEmpty())
                {
                    edphone.setError("Phone No is required");
                    edphone.requestFocus();
                }else if (phone.length()<10){
                    edphone.setError("Invalid Mobile Number");
                    edphone.requestFocus();
                }else if (UserName.isEmpty())
                {
                    edusername.setError("username is required");
                    edusername.requestFocus();
                }else if (repassword.isEmpty())
                {
                    edrepassword.setError("Re-Password is required");
                    edrepassword.requestFocus();
                }else if(!password.equals(repassword)){
                    edrepassword.setError("Passwords are mismatch");
                    edrepassword.setText("");
                    edrepassword.requestFocus();
                }else {
                    reference= FirebaseDatabase.getInstance().getReference().child("Data").child("Users").child(phone);

                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setUserName(UserName);
                    user.setPassword(password);
                    reference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_LONG).show();
                        }
                    });

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                editor=getSharedPreferences("LOGIN",MODE_PRIVATE).edit();
                                editor.putString("emailid",email);
                                editor.putString("mobile",phone);
                                editor.commit();
                                startActivity( new Intent(getApplicationContext(),HomeUI.class) );
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Registeration Error...",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        });

    }
}