package com.example.sahiwalhealthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class AdminPage extends AppCompatActivity {
    CardView doctor,pharmacist;
    LottieAnimationView dctr , phrmcy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        doctor = findViewById(R.id.clinics);
        pharmacist = findViewById(R.id.pharmacist);
        dctr = findViewById(R.id.doctor);
        phrmcy = findViewById(R.id.pharmacy);

        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f);
        animator.addUpdateListener(animation -> {
            dctr.setProgress((Float) animation.getAnimatedValue());
            phrmcy.setProgress((Float) animation.getAnimatedValue());
        });
        animator.start();

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminPage.this,DoctorLoginPanel.class);
                startActivity(i);
                finish();
            }
        });

        pharmacist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminPage.this,PharmacistLoginPanel.class);
                startActivity(i);
                finish();
            }
        });
    }
}