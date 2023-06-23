package com.example.sahiwalhealthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DoctorLoginPanel extends AppCompatActivity {

    CardView login,register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login_panel);
        login = findViewById(R.id.adminDoctorLogin);
        register = findViewById(R.id.adminDoctorRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorLoginPanel.this,DoctorRegisterPanel.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorLoginPanel.this,DoctorLogin.class);
                startActivity(i);
            }
        });

    }
}