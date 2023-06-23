package com.example.sahiwalhealthcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    EditText email,pass;
    TextView signup;
    Button login;
    CardView gmail;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    /*public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser !=null){
            Intent i = new Intent(Login.this,Home.class);
            startActivity(i);
            finish();
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        lottieAnimationView = findViewById(R.id.lottieLogin);
        progressBar = findViewById(R.id.loginprogressbar);
        email = findViewById(R.id.edtloginemail);
        pass = findViewById(R.id.edtloginpass);
        login = findViewById(R.id.userlogin);
        gmail = findViewById(R.id.gmaillogin);
        signup = findViewById(R.id.signupPage);

        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f);
        animator.addUpdateListener(animation -> {
            lottieAnimationView.setProgress((Float) animation.getAnimatedValue());
        });
        animator.start();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);

            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                SignIn();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String mail,password;
                mail = String.valueOf(email.getText());
                password = String.valueOf(pass.getText());

                if(TextUtils.isEmpty(mail))
                {
                    email.requestFocus();
                    email.setError("Empty...");
                    Toast.makeText(Login.this, "Please enter your email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.requestFocus();
                    email.setError("Please enter correct email");
                    Toast.makeText(Login.this, "Please enter correct email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    pass.requestFocus();
                    pass.setError("Empty...");
                    Toast.makeText(Login.this, "Please enter your password..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length()<7)
                {
                    pass.requestFocus();
                    pass.setError("password must be greater\nthan 7 characters");
                    Toast.makeText(Login.this, "Please enter valid password..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mail) && TextUtils.isEmpty(password))
                {
                    email.requestFocus();
                    email.setError("Empty...");
                    pass.requestFocus();
                    pass.setError("Empty...");
                    Toast.makeText(Login.this, "please enter your email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Login.this,Home.class);
                            startActivity(i);
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Invalid email or password..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void SignIn(){
        Intent i = googleSignInClient.getSignInIntent();
        startActivityForResult(i,111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==111) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                progressBar.setVisibility(View.GONE);
                Intent i = new Intent(Login.this, Home.class);
                startActivity(i);
                finish();
            }
            catch (ApiException e)
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Error...", Toast.LENGTH_SHORT).show();
            }

        }
    }

}