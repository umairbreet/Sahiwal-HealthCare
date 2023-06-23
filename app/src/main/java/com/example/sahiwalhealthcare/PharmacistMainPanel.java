package com.example.sahiwalhealthcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PharmacistMainPanel extends AppCompatActivity {
    TextView title,docname,address,opentime,closetime,zip,city,contact;
    ImageView ownimage,phimage;
    Button update ,delete;
    int t1,t2,m1,m2;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_main_panel);
        title = findViewById(R.id.clinicTitle);
        docname = findViewById(R.id.doctorTitle);
        address = findViewById(R.id.addresses);
        opentime = findViewById(R.id.opentime);
        closetime = findViewById(R.id.closeTime);
        zip = findViewById(R.id.zipCode);
        city = findViewById(R.id.city);
        contact = findViewById(R.id.contactNo);
        update = findViewById(R.id.updatePha);
        delete = findViewById(R.id.deletePha);
        ownimage = findViewById(R.id.ownerPHOTO);
        phimage = findViewById(R.id.pharmacyPHOTO);

        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        DocumentReference reference = fStore.collection("pharmacist").document(userID);

        reference.addSnapshotListener(PharmacistMainPanel.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                title.setText(value.getString("PharmacyName"));
                docname.setText(value.getString("OwnerName"));
                address.setText(value.getString("Address"));
                city.setText(value.getString("City"));
                zip.setText(value.getString("ZipCode"));
                opentime.setText(value.getString("OpenTime"));
                closetime.setText(value.getString("CloseTime"));
                contact.setText(value.getString("Phone"));
                Picasso.get().load(value.getString("PharmacyURL")).fit().into(phimage);
                Picasso.get().load(value.getString("OwnerURL")).fit().into(ownimage);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(PharmacistMainPanel.this);
                dialog.setContentView(R.layout.item_pharmacist_update);

                ImageView dismiss = dialog.findViewById(R.id.closeBtn);
                EditText clinicN,doctorN,addr,city,zipc,ph;
                TextView openT,closeT;
                Button docUpdate;
                clinicN = dialog.findViewById(R.id.pharmacyUpdate);
                doctorN = dialog.findViewById(R.id.ownerUpdate);
                addr = dialog.findViewById(R.id.addressUpdate);
                city = dialog.findViewById(R.id.cityUpdate);
                zipc = dialog.findViewById(R.id.zipUpdate);
                ph = dialog.findViewById(R.id.phoneUpdate);
                openT = dialog.findViewById(R.id.updateOpeningTime);
                closeT = dialog.findViewById(R.id.updateClosingTime);
                docUpdate = dialog.findViewById(R.id.updatingDoc);

                openT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                PharmacistMainPanel.this,
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
                                PharmacistMainPanel.this,
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
                        String opnT = openT.getText().toString();
                        String clsT = closeT.getText().toString();

                        DocumentReference documentReference = fStore.collection("pharmacist").document(userID);
                        Map<String,Object> map= new HashMap<>();

                        if (!name.isEmpty())
                        {
                            map.put("PharmacyName",name);
                        }
                        if (!docName.isEmpty())
                        {
                            map.put("OwnerName",docName);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PharmacistMainPanel.this);
                alertDialog.setTitle("Are you Sure?");
                alertDialog.setIcon(R.drawable.warning);
                alertDialog.setMessage("Once account delete it can not be recovered again!");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fStore.collection("pharmacist").document(userID)
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
                                            Toast.makeText(PharmacistMainPanel.this, "Error occurred...", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PharmacistMainPanel.this, "You clicked to cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setCancelable(false);

                AlertDialog dialogs = alertDialog.create();
                dialogs.show();
            }
        });
    }
}