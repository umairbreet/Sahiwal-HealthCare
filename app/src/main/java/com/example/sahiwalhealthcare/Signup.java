package com.example.sahiwalhealthcare;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity {
    EditText email,pass,name,phone;
    Button signup;
    TextView signin;
    CardView Gmail,upload;
    CircleImageView image;
    Uri imageUri;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseStorage storage;
    StorageReference mStorage;
    String ImageURL;
    String PicUri;
    String userID;
    String TAG = "TAG";
    int RC_SIGN_IN = 200;
    ProgressBar progressBar;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        progressBar = findViewById(R.id.singupprogressbar);
        Gmail = findViewById(R.id.gmailsignup);
        name = findViewById(R.id.edtsignupname);
        phone = findViewById(R.id.edtsignupphone);
        email = findViewById(R.id.edtsignupemail);
        pass = findViewById(R.id.edtsignuppass);
        signup = findViewById(R.id.usersignup);
        signin = findViewById(R.id.useralready);
        image = findViewById(R.id.userImage);
        upload = findViewById(R.id.selectUserImage);


        Gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                SignIn();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                UploadImage();
                PicUri = ImageURL;
                String mail,password,NAME,PHONE;
                mail = String.valueOf(email.getText());
                password = String.valueOf(pass.getText());
                NAME = String.valueOf(name.getText());
                PHONE = String.valueOf(phone.getText());


                if(TextUtils.isEmpty(mail))
                {
                    email.requestFocus();
                    email.setError("Empty...");
                    Toast.makeText(Signup.this, "Please enter your email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.requestFocus();
                    email.setError("Please enter correct email");
                    Toast.makeText(Signup.this, "Please enter correct email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    pass.requestFocus();
                    pass.setError("Empty...");
                    Toast.makeText(Signup.this, "Please enter your password..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length()<7)
                {
                    pass.requestFocus();
                    pass.setError("password must be greater\nthan 7 characters");
                    Toast.makeText(Signup.this, "Please enter valid password..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(NAME))
                {
                    name.requestFocus();
                    name.setError("Empty...");
                    Toast.makeText(Signup.this, "Please enter your FUll Name..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(PHONE))
                {
                    phone.requestFocus();
                    phone.setError("Empty...");
                    Toast.makeText(Signup.this, "Please enter your Phone Number..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (PHONE.length()<11)
                {
                    phone.requestFocus();
                    phone.setError("Enter your 11 digit phone number..");
                    Toast.makeText(Signup.this, "Please enter your correct Phone Number..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mail) && TextUtils.isEmpty(password) && TextUtils.isEmpty(NAME) && TextUtils.isEmpty(PHONE) )
                {
                    name.requestFocus();
                    name.setError("Empty...");
                    phone.requestFocus();
                    phone.setError("Empty...");
                    email.requestFocus();
                    email.setError("Empty...");
                    pass.requestFocus();
                    pass.setError("Empty...");
                    Toast.makeText(Signup.this, "please enter your data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Signup.this, "Account Created Successfully..", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();

                            DocumentReference reference = fStore.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Name",NAME);
                            user.put("Phone",PHONE);
                            user.put("URL",PicUri);

                            reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onSuccess: Users profile is created for "+userID);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure: "+e.toString());
                                }
                            });


                            Intent i = new Intent(Signup.this, Login.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Signup.this, "Authentication Failed...\n Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyLogin();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageFromGallery();
            }
        });
    }

    private void PickImageFromGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        i,
                        "Select Image from here..."),22);
    }

    private void UploadImage(){
        if (imageUri!=null){
            StorageReference storageReference = mStorage.child("UserPhoto/" + imageUri.getLastPathSegment());
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri!=null){
                                ImageURL = uri.toString();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG",e.getMessage());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void alreadyLogin(){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
    }
    private void SignIn(){
        Intent i = googleSignInClient.getSignInIntent();
        startActivityForResult(i,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 22
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            imageUri = data.getData();
            try {

                 Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                this.getContentResolver(),
                                imageUri);
                image.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

        if (requestCode==RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                progressBar.setVisibility(View.GONE);
                task.getResult(ApiException.class);
                Intent i = new Intent(Signup.this, Home.class);
                startActivity(i);
                finish();
            }
            catch (ApiException e)
            {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Google sign-in failed "+ e.getMessage());
                Toast.makeText(this,"Google sign-in failed "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}