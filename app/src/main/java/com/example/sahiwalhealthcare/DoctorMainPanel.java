package com.example.sahiwalhealthcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class DoctorMainPanel extends AppCompatActivity {

    CardView adminChat;
    TextView title,docname,address,fee,opentime,closetime,skills,zip,city,contact;
    ImageView Docimage,Cimage;
    Button update,delete;
    int m1,m2,t1,t2;

    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main_panel);

        title = findViewById(R.id.clinicTitle);
        docname = findViewById(R.id.doctorTitle);
        address = findViewById(R.id.addresses);
        fee = findViewById(R.id.fee);
        opentime = findViewById(R.id.opentime);
        closetime = findViewById(R.id.closeTime);
        skills = findViewById(R.id.skill);
        zip = findViewById(R.id.zipCode);
        city = findViewById(R.id.city);
        contact = findViewById(R.id.contactNo);
        update = findViewById(R.id.updateDoc);
        delete = findViewById(R.id.deleteDoc);
        Docimage = findViewById(R.id.dctrimg);
        Cimage = findViewById(R.id.clinicPhoto);


        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        DocumentReference reference = fStore.collection("doctors").document(userID);

        reference.addSnapshotListener(DoctorMainPanel.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                title.setText(value.getString("ClinicName"));
                docname.setText(value.getString("DoctorName"));
                address.setText(value.getString("Address"));
                city.setText(value.getString("City"));
                zip.setText(value.getString("ZipCode"));
                fee.setText(value.getString("Fee"));
                opentime.setText(value.getString("OpenTime"));
                closetime.setText(value.getString("CloseTime"));
                skills.setText(value.getString("Specialize"));
                contact.setText(value.getString("Phone"));
                Picasso.get().load(value.getString("DocUrl")).fit().into(Docimage);
                Picasso.get().load(value.getString("ClinicUrl")).fit().into(Cimage);


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(DoctorMainPanel.this);
                dialog.setContentView(R.layout.item_doctor_update);

                ImageView dismiss = dialog.findViewById(R.id.closeBtn);
                EditText clinicN,doctorN,addr,city,zipc,ph,fees;
                TextView openT,closeT;
                Button docUpdate;
                clinicN = dialog.findViewById(R.id.clinicUpdate);
                doctorN = dialog.findViewById(R.id.doctorUpdate);
                addr = dialog.findViewById(R.id.addressUpdate);
                city = dialog.findViewById(R.id.cityUpdate);
                zipc = dialog.findViewById(R.id.zipUpdate);
                ph = dialog.findViewById(R.id.phoneUpdate);
                fees = dialog.findViewById(R.id.feeUpdate);
                openT = dialog.findViewById(R.id.updateOpeningTime);
                closeT = dialog.findViewById(R.id.updateClosingTime);
                docUpdate = dialog.findViewById(R.id.updatingDoc);

                openT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                DoctorMainPanel.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        t1 = hourOfDay;
                                        m1 = minute;
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(0,0,0,t1,m1);
                                        openT.setText(DateFormat.format("hh:mm aa",calendar));

                                    }
                                },12,0,false
                        );
                        timePickerDialog.updateTime(t1,m1);
                        timePickerDialog.show();
                    }
                });
                closeT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                DoctorMainPanel.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        t2 = hourOfDay;
                                        m2 = minute;
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(0,0,0,t2,m2);
                                        closeT.setText(DateFormat.format("hh:mm aa",calendar));

                                    }
                                },12,0,false
                        );
                        timePickerDialog.updateTime(t2,m2);
                        timePickerDialog.show();
                    }
                });

                docUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = clinicN.getText().toString().trim();
                        String docName = doctorN.getText().toString().trim();
                        String addres = addr.getText().toString().trim();
                        String City = city.getText().toString().trim();
                        String ZIP = zipc.getText().toString().trim();
                        String phn = ph.getText().toString().trim();
                        String Fees = fees.getText().toString().trim();
                        String opnT = openT.getText().toString();
                        String clsT = closeT.getText().toString();

                        DocumentReference documentReference = fStore.collection("doctors").document(userID);
                        Map<String,Object> map= new HashMap<>();

                        if (name.isEmpty() && docName.isEmpty() && addres.isEmpty()
                                && City.isEmpty() && ZIP.isEmpty() && phn.isEmpty())
                        {
                            Toast.makeText(DoctorMainPanel.this, "Please enter data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!name.isEmpty())
                        {
                            map.put("ClinicName",name);
                        }
                        if (!docName.isEmpty())
                        {
                            map.put("DoctorName",docName);
                        }
                        if (!addres.isEmpty())
                        {
                            map.put("Address",addres);
                        }
                        if (!City.isEmpty())
                        {
                            map.put("City",City);
                        }
                        if (!ZIP.isEmpty())
                        {
                            map.put("ZipCode",ZIP);
                        }
                        if (!phn.isEmpty())
                        {
                            map.put("Phone",phn);
                        }
                        if (!Fees.isEmpty())
                        {
                            map.put("Fee",Fees);
                        }
                        if (opnT.length()<9){
                            map.put("OpenTime",opnT);
                        }
                        if (clsT.length()<9){
                            map.put("CloseTime",clsT);
                        }

                        documentReference.update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("TAG","onSuccess: Updated Successfully");
                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG","onFailure: "+e.getMessage());
                                    }
                                });
                    }
                });

                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DoctorMainPanel.this);
                alertDialog.setTitle("Are you Sure?");
                alertDialog.setIcon(R.drawable.warning);
                alertDialog.setMessage("Once account delete it can not be recovered again!");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fStore.collection("doctors").document(userID)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user !=null){
                                            user.delete();
                                            Log.d("TAG","Account Deleted Successfully");
                                            finish();

                                        }
                                        else{
                                            Toast.makeText(DoctorMainPanel.this, "Error occurred...", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG","Error deleting account "+e.getMessage());
                                    }
                                });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                    }
                });
                alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DoctorMainPanel.this, "You clicked to cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setCancelable(false);

                AlertDialog dialogs = alertDialog.create();
                dialogs.show();
            }
        });
    }
}