package com.example.sahiwalhealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NextPageOne extends AppCompatActivity {
    ImageView close;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page_one);
        button = findViewById(R.id.npage1);
        close  = findViewById(R.id.close);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NextPageOne.this,NextPageTw.class);
                startActivity(i);
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NextPageOne.this,SignUpLogin.class);
                startActivity(i);
                finish();
            }
        });
    }

}