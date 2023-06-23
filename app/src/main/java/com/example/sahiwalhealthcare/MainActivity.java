package com.example.sahiwalhealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button getstart;
    TextView skipbtn;
    ImageView admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getstart = findViewById(R.id.getstart);
        skipbtn = findViewById(R.id.skip);
        admin = findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AdminPage.class);
                startActivity(i);
            }
        });

        getstart.setOnClickListener(this::pageOne);

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignUpLogin.class);
                startActivity(i);
            }
        });
    }
    public void pageOne(View v){
        Intent i = new Intent(this,NextPageOne.class);
        startActivity(i);
    }
}