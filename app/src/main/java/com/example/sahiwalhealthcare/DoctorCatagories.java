package com.example.sahiwalhealthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DoctorCatagories extends AppCompatActivity {
    CardView asthma,bones,cancer,cough,dentist,diabetes,ear,eyes,gynecologist,heart
            ,immunologist,kidney,liver,pediatric,physician,physiotherapist,psychiatrist
            ,skin,stomach,all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_catagories);
        asthma = findViewById(R.id.asthma);
        bones = findViewById(R.id.bones);
        cancer = findViewById(R.id.cancer);
        cough = findViewById(R.id.cough);
        dentist = findViewById(R.id.dentist);
        diabetes = findViewById(R.id.diabetes);
        ear = findViewById(R.id.ear);
        eyes = findViewById(R.id.eyes);
        gynecologist = findViewById(R.id.gynecologist);
        heart = findViewById(R.id.heart);
        immunologist = findViewById(R.id.immunologist);
        kidney = findViewById(R.id.kidney);
        liver = findViewById(R.id.liver);
        pediatric = findViewById(R.id.pediatric);
        physician = findViewById(R.id.physician);
        physiotherapist = findViewById(R.id.physiotherapist);
        psychiatrist = findViewById(R.id.psychiatrist);
        skin = findViewById(R.id.skin);
        stomach = findViewById(R.id.stomach);
        all = findViewById(R.id.alldocs);

        asthma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Asthma.class);
                startActivity(i);
            }
        });

        bones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Bones.class);
                startActivity(i);
            }
        });

        cancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Cancer.class);
                startActivity(i);
            }
        });

        cough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Cough.class);
                startActivity(i);
            }
        });

        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Dentist.class);
                startActivity(i);
            }
        });

        diabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Diabetes.class);
                startActivity(i);
            }
        });

        ear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Ear.class);
                startActivity(i);
            }
        });

        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Eyes.class);
                startActivity(i);
            }
        });

        gynecologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Gynecologist.class);
                startActivity(i);
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Heart.class);
                startActivity(i);
            }
        });

        immunologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Immunologist.class);
                startActivity(i);
            }
        });

        kidney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Kidney.class);
                startActivity(i);
            }
        });

        liver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Liver.class);
                startActivity(i);
            }
        });

        pediatric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Pediatric.class);
                startActivity(i);
            }
        });

        physician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Physician.class);
                startActivity(i);
            }
        });

        physiotherapist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Physciotherapist.class);
                startActivity(i);
            }
        });

        psychiatrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Psychiatrist.class);
                startActivity(i);
            }
        });

        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Skin.class);
                startActivity(i);
            }
        });

        stomach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Stomach.class);
                startActivity(i);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorCatagories.this, Appointment.class);
                startActivity(i);
            }
        });
    }
}