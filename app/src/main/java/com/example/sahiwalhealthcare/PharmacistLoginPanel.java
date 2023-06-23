package com.example.sahiwalhealthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PharmacistLoginPanel extends AppCompatActivity {
 CardView login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_login_panel);
        login = findViewById(R.id.adminPharmacistLogin);
        register = findViewById(R.id.adminPharmacistRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PharmacistLoginPanel.this, PharmacistRegiterPanel.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PharmacistLoginPanel.this, PharmacistLogin.class);
                startActivity(i);
            }
        });

    }
}