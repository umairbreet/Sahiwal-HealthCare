package com.example.sahiwalhealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class SignUpLogin extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    Button login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);
        lottieAnimationView = findViewById(R.id.lottieLogin);
        login = findViewById(R.id.Login);
        signup = findViewById(R.id.SignUp);

        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f);
        animator.addUpdateListener(animation -> {
            lottieAnimationView.setProgress((Float) animation.getAnimatedValue());
        });
        animator.start();

        signup.setOnClickListener(this::signUp);
        login.setOnClickListener(this::logIn);
    }

    public void signUp(View v)
    {
        Intent i = new Intent(this,Signup.class);
        startActivity(i);
    }
    public void logIn(View v){
        Intent i = new Intent(this,Login.class);
        startActivity(i);

    }
}