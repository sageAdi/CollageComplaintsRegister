package com.example.complaintsregisterpart2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class Electrician extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    FirebaseFirestore db;
    String nameid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician);
        recyclerView=findViewById(R.id.recyclerView);
        db=FirebaseFirestore.getInstance();
        recyclerViewSetUp();
        nameid=getIntent().getStringExtra("name");
    }
    public void recyclerViewSetUp() {
        Query query=FirebaseFirestore.getInstance().collection("complaints");
        FirestoreRecyclerOptions<Model> options=new FirestoreRecyclerOptions.Builder<Model>()
                .setQuery(query,Model.class)
                .build();
        adapter=new Adapter(options);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Electrician.this));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final Model model=documentSnapshot.toObject(Model.class);
                Intent intent=new Intent(getApplicationContext(),Complaints_Response.class);
                intent.putExtra("Room",model.getRoomDetail());
                intent.putExtra("Complaint",model.getComplaint());
                intent.putExtra("id",documentSnapshot.getId());
                intent.putExtra("otp",model.getOtp());
                intent.putExtra("email",model.getEmail());
                intent.putExtra("Name",nameid);
                intent.putExtra("hostel",model.getHostel());
                intent.putExtra("Date",model.getDate());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
