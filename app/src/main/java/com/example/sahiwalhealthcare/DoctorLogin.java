package com.example.sahiwalhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorLogin extends AppCompatActivity {
    EditText email,password;
    TextView registerPanel;
    Button login;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        email = findViewById(R.id.doctorEmail);
        password = findViewById(R.id.doctorPassword);
        login = findViewById(R.id.doctorLogin);
        progressBar = findViewById(R.id.progressBarAdmin);
        registerPanel = findViewById(R.id.registerPanel);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        registerPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorLogin.this,DoctorRegisterPanel.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if (mail.isEmpty() && pass.isEmpty())
                {
                    email.requestFocus();
                    email.setError("Empty...");
                    email.requestFocus();
                    email.setError("Please enter correct email");
                    Toast.makeText(DoctorLogin.this, "Please enter Email and Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mail))
                {
                    email.requestFocus();
                    email.setError("Empty...");
                    Toast.makeText(DoctorLogin.this, "Please enter your email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.requestFocus();
                    email.setError("Please enter correct email");
                    Toast.makeText(DoctorLogin.this, "Please enter correct email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    password.requestFocus();
                    password.setError("Empty...");
                    Toast.makeText(DoctorLogin.this, "Please enter your password..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length()<7)
                {
                    password.requestFocus();
                    password.setError("password must be greater\nthan 7 characters");
                    Toast.makeText(DoctorLogin.this, "Please enter valid password..", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(DoctorLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(DoctorLogin.this, DoctorMainPanel.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(DoctorLogin.this, "Incorrect user name or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}