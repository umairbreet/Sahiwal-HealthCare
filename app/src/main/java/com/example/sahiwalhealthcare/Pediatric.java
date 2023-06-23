package com.example.sahiwalhealthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Pediatric extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    DoctorModel doctorModels;
    ArrayList<DoctorModel> model;
    DoctorAdapter adapter;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pediatric);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.searchViewDoc);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        fStore = FirebaseFirestore.getInstance();
        model = new ArrayList< >();
        adapter = new DoctorAdapter(this,model);
        recyclerView.setAdapter(adapter);

        fStore.collection("doctors").whereEqualTo("Specialize","Pediatric")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progressBar.setVisibility(View.GONE);
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds:list){
                            doctorModels = ds.toObject(DoctorModel.class);
                            model.add(doctorModels);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void filterList(String newText) {
        ArrayList<DoctorModel> filterList = new ArrayList<>();
        for (DoctorModel model1 : model){
            if (model1.getDoctorName().toLowerCase().contains(newText.toLowerCase())){
                filterList.add(model1);
            }
        }

        if (filterList.isEmpty()){
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }else {
            adapter.setFilteredList(filterList);
        }
    }
}