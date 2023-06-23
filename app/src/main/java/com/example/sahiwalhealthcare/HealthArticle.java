package com.example.sahiwalhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HealthArticle extends AppCompatActivity {
    ImageSlider imageSlider;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article);
        imageSlider = findViewById(R.id.imageSliders);
        back = findViewById(R.id.backHealthArticle);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ArrayList<SlideModel> slideModels = new ArrayList<>();


        slideModels.add(new SlideModel(R.drawable.a, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.b, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.c, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.d, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.e, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.f, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.g, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.h, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.i, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.j, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.k, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.l, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.m, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.n, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.o, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.p, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.q, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.r, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);






    }
}