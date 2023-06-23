package com.example.sahiwalhealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NextPageTw extends AppCompatActivity {
    ImageView close;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page_tw);
        button = findViewById(R.id.npagetwo);
        close = findViewById(R.id.close_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NextPageTw.this,NextPageThree.class);
                startActivity(i);
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NextPageTw.this,SignUpLogin.class);
                startActivity(i);
                finish();
            }
        });

    }
}