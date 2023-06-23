package com.example.sahiwalhealthcare;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    Context context;
    ArrayList<DoctorModel> doctorModels;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    public void setFilteredList(ArrayList<DoctorModel> filteredList){
        this.doctorModels = filteredList;
        notifyDataSetChanged();
    }
    public DoctorAdapter(Context context, ArrayList<DoctorModel> doctorModels) {
        this.context = context;
        this.doctorModels = doctorModels;
    }

    @NonNull
    @Override
    public DoctorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_doctor,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.MyViewHolder holder, int position) {

        String ClinicName = doctorModels.get(position).getClinicName();
        String Address = doctorModels.get(position).getAddress();
        String Fee = doctorModels.get(position).getFee();
        String Open = doctorModels.get(position).getOpenTime();
        String Close = doctorModels.get(position).getCloseTime();
        String skill = doctorModels.get(position).getSpecialize();
        String Phone = doctorModels.get(position).getPhone().trim();
        String clinicImage = doctorModels.get(position).getClinicUrl();

        holder.docTitle.setText(doctorModels.get(position).getDoctorName());
        holder.address.setText(doctorModels.get(position).getAddress());
        holder.city.setText(doctorModels.get(position).getCity());
        holder.zip.setText(doctorModels.get(position).getZipCode());

        Picasso.get().load(doctorModels.get(position).getDocUrl()).fit().into(holder.docPhoto);

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_doctor_dialog);
                ImageView dismiss = dialog.findViewById(R.id.docDialogClose);
                ImageView clinicPhoto;
                TextView dialogClinicTitle,dialogAddress,dialogFee,dialogOpen,dialogClose,dialogSkill;
                CardView dialogPhone;

                        dialogClinicTitle = dialog.findViewById(R.id.docDialogClinicTitle);
                        dialogAddress = dialog.findViewById(R.id.docDialogAddress);
                        dialogFee = dialog.findViewById(R.id.docDialogfee);
                        dialogOpen = dialog.findViewById(R.id.docDialogopentime);
                        dialogClose = dialog.findViewById(R.id.docDialogcloseTime);
                        dialogSkill = dialog.findViewById(R.id.docDialogSkill);
                        dialogPhone = dialog.findViewById(R.id.docDialogContact);
                        clinicPhoto = dialog.findViewById(R.id.clinicPhoto);
                dialogClinicTitle.setText(ClinicName);
                dialogAddress.setText(Address);
                dialogFee.setText(Fee);
                dialogOpen.setText(Open);
                dialogClose.setText(Close);
                dialogSkill.setText(skill);

                Picasso.get().load(clinicImage).fit().into(clinicPhoto);

                dialogPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                            Intent i = new Intent(Intent.ACTION_DIAL);
                            i.setData(Uri.parse("tel:"+Phone));
                            context.startActivity(i);
                            dialog.dismiss();
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
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_doctor_dialog);
                ImageView dismiss = dialog.findViewById(R.id.docDialogClose);
                ImageView clinicPhoto;
                TextView dialogClinicTitle,dialogAddress,dialogFee,dialogOpen,dialogClose,dialogSkill;
                CardView dialogPhone;

                dialogClinicTitle = dialog.findViewById(R.id.docDialogClinicTitle);
                dialogAddress = dialog.findViewById(R.id.docDialogAddress);
                dialogFee = dialog.findViewById(R.id.docDialogfee);
                dialogOpen = dialog.findViewById(R.id.docDialogopentime);
                dialogClose = dialog.findViewById(R.id.docDialogcloseTime);
                dialogSkill = dialog.findViewById(R.id.docDialogSkill);
                dialogPhone = dialog.findViewById(R.id.docDialogContact);
                clinicPhoto = dialog.findViewById(R.id.clinicPhoto);

                dialogClinicTitle.setText(ClinicName);
                dialogAddress.setText(Address);
                dialogFee.setText(Fee);
                dialogOpen.setText(Open);
                dialogClose.setText(Close);
                dialogSkill.setText(skill);

                Picasso.get().load(clinicImage).fit().into(clinicPhoto);

                dialogPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent i = new Intent(Intent.ACTION_DIAL);
                        i.setData(Uri.parse("tel:"+Phone));
                        context.startActivity(i);
                        dialog.dismiss();
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

    }


    @Override
    public int getItemCount() {
        return doctorModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView docTitle,address,city,zip;
        ImageView docPhoto;
        CardView details,detail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            docTitle = itemView.findViewById(R.id.itemDocClinic);
            address = itemView.findViewById(R.id.itemDocAddress);
            details = itemView.findViewById(R.id.itemDocDetails);
            detail = itemView.findViewById(R.id.itemDocDetail);
            city = itemView.findViewById(R.id.itemDocCity);
            zip = itemView.findViewById(R.id.itemDocZipcode);
            docPhoto = itemView.findViewById(R.id.mrDoc);

        }
    }
}
