package com.example.sahiwalhealthcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {
    CardView appointments,pharmacies,healthArticle,aboutUS,ai,logout;
    ImageView userImage;
    TextView nameDisplay;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout = findViewById(R.id.logout);
        appointments = findViewById(R.id.appointment);
        healthArticle = findViewById(R.id.article);
        pharmacies = findViewById(R.id.homePharmacy);
        aboutUS = findViewById(R.id.aboutus);
        nameDisplay = findViewById(R.id.userNameDisp);
        userImage = findViewById(R.id.userPic);
        ai = findViewById(R.id.assistent);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null)
        {
            String name = account.getDisplayName();
            nameDisplay.setText(name);
            Uri uri = account.getPhotoUrl();
            if (uri!=null)
            {
                String URL = uri.toString();
                Picasso.get().load(URL).fit().into(userImage);
            }


        }

        DocumentReference reference = fStore.collection("Users").document(userID);
        reference.addSnapshotListener(Home.this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nameDisplay.setText(value.getString("Name"));
                Picasso.get().load(value.getString("URL")).into(userImage);
            }
        });


        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,DoctorCatagories.class);
                startActivity(i);
            }
        });

        pharmacies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Pharmacy.class);
                startActivity(i);
            }
        });

        healthArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,HealthArticle.class);
                startActivity(i);
            }
        });
        aboutUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, AboutUs.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SignOut();
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SignOut();
                    }
                });
            }

            private void SignOut() {
                Intent i = new Intent(Home.this,SignUpLogin.class);
                startActivity(i);
                finish();
            }
        });

        ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,chatbot.class);
                startActivity(i);
            }
        });
    }
}