package com.example.complaintregister;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditComplaint extends AppCompatActivity {

    EditText roomNo,complaint,hostel_name;
    Button submit;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_complaint);
        roomNo=findViewById(R.id.roomDetails);
        complaint=findViewById(R.id.complaintDetails);
        hostel_name=findViewById(R.id.hostelDetails);
        submit=findViewById(R.id.submitComplaintBtn);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComplaint();
            }
        });
    }
    private void submitComplaint() {
        if (TextUtils.isEmpty(roomNo.getText().toString())){
            Toast.makeText(this, "Please enter Room Details", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(hostel_name.getText().toString())){
            Toast.makeText(this, "Please enter Hostel Details", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(complaint.getText().toString())){
            Toast.makeText(this, "Please enter Complaint", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c);
            final int otp=(int)(Math.random()*9000)+1000;
            final Map<String, Object> user =new HashMap<>();
            user.put("roomDetail",roomNo.getText().toString());
            user.put("complaint",complaint.getText().toString());
            user.put("hostel",hostel_name.getText().toString());
            user.put("status","0");
            user.put("otp",String.valueOf(otp));
            user.put("email",firebaseUser.getEmail());
            user.put("date",formattedDate);


            firebaseFirestore.collection("complaints")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(final DocumentReference documentReference) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder=new AlertDialog.Builder(EditComplaint.this);
                            builder.setTitle("Otp ")
                                    .setMessage("Your otp no is : "+otp)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent(EditComplaint.this,Profile.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    });
        }

    }
}
