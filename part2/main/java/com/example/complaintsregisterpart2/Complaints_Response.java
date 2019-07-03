package com.example.complaintsregisterpart2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Complaints_Response extends AppCompatActivity {

    TextView roomDetails,complaints,response_text;
    Button btn,cancle;
    EditText response;
    String status;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints__response);
        roomDetails=findViewById(R.id.roomText);
        complaints=findViewById(R.id.complaintText);
        btn=findViewById(R.id.okBtn);
        cancle=findViewById(R.id.cancleBtn);
        response=findViewById(R.id.response);
        response_text=findViewById(R.id.responseText);
        roomDetails.setText("Room Details : "+getIntent().getStringExtra("Room"));
        complaints.setText("Complaint : "+getIntent().getStringExtra("Complaint"));
        db=FirebaseFirestore.getInstance();
        db.collection("complaints").document(getIntent().getStringExtra("id"))
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        status=documentSnapshot.getString("status");
                        if (status.equals("0")){
                            response.setVisibility(View.GONE);
                            response_text.setText("Press Ok to take complaint");
                            btn.setVisibility(View.VISIBLE);
                            cancle.setVisibility(View.VISIBLE);
                        }
                        else if (status.equals("1")){
                            response.setVisibility(View.VISIBLE);
                            response_text.setText("Enter Otp and press Ok");
                            btn.setVisibility(View.VISIBLE);
                            cancle.setVisibility(View.VISIBLE);
                        }
                        if (status.equals("2")){
                            response_text.setText("Complaint Successfully Completed!!!");
                            btn.setVisibility(View.GONE);
                            response.setVisibility(View.GONE);
                            cancle.setVisibility(View.GONE);
                        }
                    }
                });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("0")){
                    status="1";
                }
                if (response.getText().toString().equals(getIntent().getStringExtra("otp"))){
                    status="2";
                    response_text.setText("Complaint Successfully Completed!!!");
                    btn.setVisibility(View.GONE);
                    response.setVisibility(View.GONE);
                }
                Map<String, Object> user =new HashMap<>();
                user.put("roomDetail",getIntent().getStringExtra("Room"));
                user.put("complaint",getIntent().getStringExtra("Complaint"));
                user.put("status",status);
                user.put("otp",getIntent().getStringExtra("otp"));
                user.put("email",getIntent().getStringExtra("email"));
                user.put("electrician",getIntent().getStringExtra("Name"));
                user.put("hostel",getIntent().getStringExtra("hostel"));
                user.put("date",getIntent().getStringExtra("Date"));
                db.collection("complaints").document(getIntent().getStringExtra("id"))
                        .set(user);
                Intent intent=new Intent(getApplicationContext(),Electrician.class);
                startActivity(intent);
                finish();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Complaints_Response.this,Electrician.class));
                finish();
            }
        });
    }
}
