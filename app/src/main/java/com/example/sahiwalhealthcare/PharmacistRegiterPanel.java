package com.example.sahiwalhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PharmacistRegiterPanel extends AppCompatActivity {
    EditText clinicName,doctorName,city,zipCode,address,email,pass,contact;
//    PharmacyURL
    CircleImageView pharmacy,owner;
    CardView selectPharmacy, selectOwner;
    Uri phUri,ownUri;
    String phPhotoUrl,ownPhotoUrl;
    TextView openTimeSelect,closeTimeSelect;
    int t1hour,t2hour,t1minute,t2minute;
    Button registerPharmacy;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseStorage storage;
    StorageReference mStorage;
    PharmacyModel model;
    String userID;
    public static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_regiter_panel);
        email = findViewById(R.id.edtEmail);
        pass = findViewById(R.id.edtPassword);
        contact = findViewById(R.id.edtContact);
        clinicName = findViewById(R.id.edtClinicName);
        doctorName = findViewById(R.id.edtDoctorName);
        city = findViewById(R.id.edtDoctorCity);
        zipCode = findViewById(R.id.edtDoctorZipcode);
        address = findViewById(R.id.edtDoctorAddress);
        openTimeSelect = findViewById(R.id.doctorOpeningTime);
        closeTimeSelect = findViewById(R.id.doctorClosingTime);
        registerPharmacy = findViewById(R.id.btnDoctorSubmit);
        progressBar = findViewById(R.id.progressBarAdminP);
        selectPharmacy = findViewById(R.id.selectPharmacyImage);
        selectOwner = findViewById(R.id.selectOwnerImage);
        pharmacy = findViewById(R.id.pharmacyImage);
        owner = findViewById(R.id.ownerImage);


        mAuth = FirebaseAuth.getInstance();
        fStore  = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference();

        openTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        PharmacistRegiterPanel.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1hour = hourOfDay;
                                t1minute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,t1hour,t1minute);
                                openTimeSelect.setText(DateFormat.format("hh:mm aa",calendar));

                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t1hour,t1minute);
                timePickerDialog.show();
            }
        });
        closeTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        PharmacistRegiterPanel.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t2hour = hourOfDay;
                                t2minute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,t2hour,t2minute);
                                closeTimeSelect.setText(DateFormat.format("hh:mm aa",calendar));

                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t2hour,t2minute);
                timePickerDialog.show();
            }
        });

        registerPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                uploadOwnerImage();
                uploadClinicImage();
                String mail = email.getText().toString();
                String password = pass.getText().toString();
                String pharmacy = clinicName.getText().toString();
                String Owner = doctorName.getText().toString();
                String phone = contact.getText().toString();
                String City = city.getText().toString();
                String zip = zipCode.getText().toString();
                String Address = address.getText().toString();
                String openTime = openTimeSelect.getText().toString();
                String closeTime = closeTimeSelect.getText().toString();

                if (mail.isEmpty() && password.isEmpty()){
                    email.requestFocus();
                    email.setError("Empty..");
                    pass.requestFocus();
                    pass.setError("Empty...");
                    Toast.makeText(PharmacistRegiterPanel.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pharmacy.isEmpty() && Owner.isEmpty() && City.isEmpty() && zip.isEmpty() && Address.isEmpty())
                {
                    clinicName.requestFocus();
                    clinicName.setError("Empty..");
                    doctorName.requestFocus();
                    doctorName.setError("Empty..");
                    city.requestFocus();
                    city.setError("Empty..");
                    zipCode.requestFocus();
                    zipCode.setError("Empty..");
                    address.requestFocus();
                    address.setError("Empty..");
                    Toast.makeText(PharmacistRegiterPanel.this, "Please enter data in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.requestFocus();
                    email.setError("Empty..");
                    Toast.makeText(PharmacistRegiterPanel.this, "Please enter correct email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    pass.requestFocus();
                    pass.setError("Empty...");
                    Toast.makeText(PharmacistRegiterPanel.this, "Please enter your password..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pharmacy.isEmpty())
                {
                    clinicName.requestFocus();
                    clinicName.setError("Empty..");
                    Toast.makeText(PharmacistRegiterPanel.this, "Please enter clinic name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Owner.isEmpty())
                {
                    doctorName.requestFocus();
                    doctorName.setError("Empty..");
                    Toast.makeText(PharmacistRegiterPanel.this, "Please enter doctor name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (City.isEmpty())
                {
                    city.requestFocus();
                    city.setError("Empty..");
                    Toast.makeText(PharmacistRegiterPanel.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (zip.isEmpty())
                {
                    zipCode.requestFocus();
                    zipCode.setError("Empty..");
                    Toast.makeText(PharmacistRegiterPanel.this, "Please enter Zip Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Address.isEmpty())
                {
                    address.requestFocus();
                    address.setError("Empty..");
                    Toast.makeText(PharmacistRegiterPanel.this, "Please enter complete address", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(PharmacistRegiterPanel.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();

                            DocumentReference reference = fStore.collection("pharmacist").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("PharmacyName",pharmacy);
                            user.put("OwnerName",Owner);
                            user.put("Phone",phone);
                            user.put("City",City);
                            user.put("ZipCode",zip);
                            user.put("Address",Address);
                            user.put("OpenTime",openTime);
                            user.put("CloseTime",closeTime);
                            user.put("PharmacyURL",phPhotoUrl);
                            user.put("OwnerURL",ownPhotoUrl);

                            reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressBar.setVisibility(View.GONE);
                                    Log.d(TAG,"onSuccess: doctor profile is created for "+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Log.d(TAG,"onFailure: "+e.toString());
                                }
                            });

                            Intent i = new Intent(PharmacistRegiterPanel.this,PharmacistLogin.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(PharmacistRegiterPanel.this, "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        selectPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPharmacyImageFromGallery();
            }
        });
        selectOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickOwnerImageFromGallery();
            }
        });

    }

    private void pickOwnerImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),34);

    }

    private void pickPharmacyImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),44);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 34
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            ownUri = data.getData();
            try {

                Bitmap bitmap1 = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                this.getContentResolver(),
                                ownUri);
                owner.setImageBitmap(bitmap1);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

        if (requestCode == 44
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            phUri = data.getData();
            try {

                Bitmap bitmap2 = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                this.getContentResolver(),
                                phUri);
                pharmacy.setImageBitmap(bitmap2);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadOwnerImage() {
        if (ownUri != null) {
            StorageReference myRef2 = mStorage.child("OwnerPhoto/" + ownUri.getLastPathSegment());
            myRef2.putFile(ownUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri!=null){
                                ownPhotoUrl = uri.toString();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,e.getMessage());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG,e.getMessage());
                    Toast.makeText(PharmacistRegiterPanel.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void uploadClinicImage(){
        if (phUri !=null){
            StorageReference myRef3 = mStorage.child("PharmacyPhoto/"+phUri.getLastPathSegment());
            myRef3.putFile(phUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri!=null){
                                phPhotoUrl = uri.toString();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PharmacistRegiterPanel.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}