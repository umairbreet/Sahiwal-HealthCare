package com.example.sahiwalhealthcare;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorRegisterPanel extends AppCompatActivity {

    EditText clinicName,doctorName,city,zipCode,address,email,pass,fee,contact;
    Spinner skills;
    TextView openTimeSelect,closeTimeSelect;
    int t1hour,t2hour,t1minute,t2minute;
    CardView selectDoc,selectClinic;
    CircleImageView doc,clinic;
    Uri docUri,clinicUri;
    String docPhotoUrl,clinicPhotoUrl;
    Button registerClinic;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseStorage storage;
    StorageReference mStorage;
    String userID;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register_panel);
        email = findViewById(R.id.edtEmail);
        pass = findViewById(R.id.edtPassword);
        clinicName = findViewById(R.id.edtClinicName);
        doctorName = findViewById(R.id.edtDoctorName);
        contact = findViewById(R.id.edtDoctorContact);
        city = findViewById(R.id.edtDoctorCity);
        zipCode = findViewById(R.id.edtDoctorZipcode);
        address = findViewById(R.id.edtDoctorAddress);
        skills = findViewById(R.id.edtDoctorSpecialization);
        fee = findViewById(R.id.edtDoctorFees);
        openTimeSelect = findViewById(R.id.doctorOpeningTime);
        closeTimeSelect = findViewById(R.id.doctorClosingTime);
        registerClinic = findViewById(R.id.btnDoctorSubmit);
        progressBar = findViewById(R.id.progressBarAdminr);
        selectDoc = findViewById(R.id.selectDoctorImage);
        doc = findViewById(R.id.docImage);
        selectClinic = findViewById(R.id.selectClinicImage);
        clinic = findViewById(R.id.clinicImage);



        mAuth = FirebaseAuth.getInstance();
        fStore  = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference();

        openTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        DoctorRegisterPanel.this,
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
                        DoctorRegisterPanel.this,
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
        itemsList();

        registerClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                uploadDoctorImage();
                uploadClinicImage();
                String mail = email.getText().toString();
                String password = pass.getText().toString();
                String Clinic = clinicName.getText().toString();
                String Doctor = doctorName.getText().toString();
                String phone = contact.getText().toString();
                String City = city.getText().toString();
                String zip = zipCode.getText().toString();
                String Address = address.getText().toString();
                String specialize = skills.getSelectedItem().toString();
                String Fee = fee.getText().toString();
                String openTime = openTimeSelect.getText().toString();
                String closeTime = closeTimeSelect.getText().toString();

                if (mail.isEmpty() && password.isEmpty()){
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(mail))
                {
                    email.requestFocus();
                    email.setError("Empty...");
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter your email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.requestFocus();
                    email.setError("Please enter correct email");
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter correct email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    pass.requestFocus();
                    pass.setError("Empty...");
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter your password..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length()<7)
                {
                    pass.requestFocus();
                    pass.setError("password must be greater\nthan 7 characters");
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter valid password..", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Clinic.isEmpty() && Doctor.isEmpty() && City.isEmpty() && zip.isEmpty() && Address.isEmpty())
                {
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter data in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Clinic.isEmpty())
                {
                    clinicName.requestFocus();
                    clinicName.setError("Empty..");
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter clinic name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Doctor.isEmpty())
                {
                    doctorName.requestFocus();
                    doctorName.setError("Empty..");
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter doctor name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (City.isEmpty())
                {
                    city.requestFocus();
                    city.setError("Empty..");
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (zip.isEmpty())
                {
                    zipCode.requestFocus();
                    zipCode.setError("Empty..");
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter Zip Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Address.isEmpty())
                {
                    address.requestFocus();
                    address.setError("Empty..");
                    Toast.makeText(DoctorRegisterPanel.this, "Please enter complete address", Toast.LENGTH_SHORT).show();
                    return;
                }


               mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(DoctorRegisterPanel.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();

                            DocumentReference reference = fStore.collection("doctors").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("ClinicName",Clinic);
                            user.put("DoctorName",Doctor);
                            user.put("Phone",phone);
                            user.put("City",City);
                            user.put("ZipCode",zip);
                            user.put("Address",Address);
                            user.put("Specialize",specialize);
                            user.put("Fee",Fee);
                            user.put("OpenTime",openTime);
                            user.put("CloseTime",closeTime);
                            user.put("DocUrl",docPhotoUrl);
                            user.put("ClinicUrl",clinicPhotoUrl);

                            reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onSuccess: doctor profile is created for "+userID);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Log.d(TAG,"onFailure: "+e.toString());
                                   }
                            });

                            Intent i = new Intent(DoctorRegisterPanel.this,DoctorLogin.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(DoctorRegisterPanel.this, "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                });

            }
        });

        selectDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDoctorImageFromGallery();
            }
        });
        selectClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickClinicImageFromGallery();
            }
        });
    }

    private void pickDoctorImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),22);
    }
    private void pickClinicImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),33);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 22
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            docUri = data.getData();
            try {

                Bitmap bitmap1 = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                this.getContentResolver(),
                                docUri);
                doc.setImageBitmap(bitmap1);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

        if (requestCode == 33
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            clinicUri = data.getData();
            try {

                Bitmap bitmap2 = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                this.getContentResolver(),
                                clinicUri);
                clinic.setImageBitmap(bitmap2);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadDoctorImage() {
        if (docUri != null) {
            StorageReference myRef0 = mStorage.child("DoctorPhoto/" + docUri.getLastPathSegment());
            myRef0.putFile(docUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myRef0.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri!=null){
                                docPhotoUrl = uri.toString();
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
                    Toast.makeText(DoctorRegisterPanel.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void uploadClinicImage(){
        if (clinicUri !=null){
            StorageReference myRef1 = mStorage.child("ClinicPhoto/"+clinicUri.getLastPathSegment());
            myRef1.putFile(clinicUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri!=null){
                                clinicPhotoUrl = uri.toString();
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
                    Toast.makeText(DoctorRegisterPanel.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void itemsList() {
        String[] items=new String[]{"Specialities","Eyes","Liver","Ear",
                "Pediatric","Stomach","Kidney","Bones"
                ,"Skin","Psychiatrist","Diabetes","Heart"
                ,"Cough","Asthma","Physician","Dentist"
                ,"Immunologist","Gynecologist"
                ,"Cancer","Physiotherapist"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DoctorRegisterPanel.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,items);
        skills.setAdapter(adapter);
    }

}