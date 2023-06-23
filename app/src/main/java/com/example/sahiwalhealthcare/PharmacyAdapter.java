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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.MyViewHolder> {

    Context context;
    ArrayList<PharmacyModel> pharmacyModels;

    public void setFilteredList(ArrayList<PharmacyModel> filteredList){
        this.pharmacyModels = filteredList;
        notifyDataSetChanged();
    }

    public PharmacyAdapter(Context context, ArrayList<PharmacyModel> pharmacyModels) {
        this.context = context;
        this.pharmacyModels = pharmacyModels;
    }

    @NonNull
    @Override
    public PharmacyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_pharmacy,parent,false);


        return new PharmacyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyAdapter.MyViewHolder holder, int position) {

        String PharmacyName = pharmacyModels.get(position).getPharmacyName();
        String Address = pharmacyModels.get(position).getAddress();
        String OpenTime = pharmacyModels.get(position).getOpenTime();
        String CloseTime = pharmacyModels.get(position).getCloseTime();
        String Phone = pharmacyModels.get(position).getPhone();
        String phURL = pharmacyModels.get(position).getPharmacyURL();

        holder.pharmacy.setText(pharmacyModels.get(position).getPharmacyName());
        holder.address.setText(pharmacyModels.get(position).getAddress());
        holder.city.setText(pharmacyModels.get(position).getCity());
        holder.zip.setText(pharmacyModels.get(position).getZipCode());

        Picasso.get().load(pharmacyModels.get(position).getOwnerURL()).fit().into(holder.owner);

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_pharmacist_dialog);
                ImageView dismiss = dialog.findViewById(R.id.docDialogClose);
                ImageView phar;
                TextView dialogClinicTitle,dialogAddress,dialogOpen,dialogClose,dialogSkill;
                CardView dialogPhone;

                dialogClinicTitle = dialog.findViewById(R.id.DialogClinicTitle);
                dialogAddress = dialog.findViewById(R.id.DialogAddress);
                dialogOpen = dialog.findViewById(R.id.Dialogopentime);
                dialogClose = dialog.findViewById(R.id.DialogcloseTime);
                dialogPhone = dialog.findViewById(R.id.DialogContact);
                phar = dialog.findViewById(R.id.phImage);

                dialogClinicTitle.setText(PharmacyName);
                dialogAddress.setText(Address);
                dialogOpen.setText(OpenTime);
                dialogClose.setText(CloseTime);
                Picasso.get().load(phURL).fit().into(phar);

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
                dialog.setContentView(R.layout.custom_pharmacist_dialog);
                ImageView dismiss = dialog.findViewById(R.id.docDialogClose);
                ImageView phar;
                TextView dialogClinicTitle,dialogAddress,dialogOpen,dialogClose,dialogSkill;
                CardView dialogPhone;

                dialogClinicTitle = dialog.findViewById(R.id.DialogClinicTitle);
                dialogAddress = dialog.findViewById(R.id.DialogAddress);
                dialogOpen = dialog.findViewById(R.id.Dialogopentime);
                dialogClose = dialog.findViewById(R.id.DialogcloseTime);
                dialogPhone = dialog.findViewById(R.id.DialogContact);
                phar = dialog.findViewById(R.id.phImage);

                dialogClinicTitle.setText(PharmacyName);
                dialogAddress.setText(Address);
                dialogOpen.setText(OpenTime);
                dialogClose.setText(CloseTime);
                Picasso.get().load(phURL).fit().into(phar);

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
        return pharmacyModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pharmacy,address,city,zip;
        ImageView owner;
        CardView details,detail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pharmacy = itemView.findViewById(R.id.itemPharmacy);
            address = itemView.findViewById(R.id.itemAddress);
            details = itemView.findViewById(R.id.itemDetails);
            detail = itemView.findViewById(R.id.detail);
            city = itemView.findViewById(R.id.itemCity);
            zip = itemView.findViewById(R.id.itemZipcode);
            owner = itemView.findViewById(R.id.mrOwner);

        }
    }
}
