package com.example.complaintregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    EditText email,pass;
    Button login_btn;
    String emailtext="email";
    String passtext;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email=findViewById(R.id.emailLogIn);
        pass=findViewById(R.id.passLogIn);
        login_btn=findViewById(R.id.login_btn);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void login() {
        emailtext=email.getText().toString().trim();
        passtext=pass.getText().toString().trim();
        firebaseAuth=FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(emailtext) && TextUtils.isEmpty(passtext)){
            Toast.makeText(this,"Both fields are empty",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailtext)){
            Toast.makeText(this,"Roll No field is empty",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(passtext)){
            Toast.makeText(this,"Password field is empty",Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            databaseReference= FirebaseDatabase.getInstance().getReference().child(emailtext+"/emailAddress");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String email=dataSnapshot.getValue().toString().trim();
                    firebaseAuth.signInWithEmailAndPassword(email,passtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(LogIn.this,Profile.class));
                                    progressDialog.dismiss();
                                    finish();
                                }
                                else {
                                    Toast.makeText(LogIn.this, "PLease Verify Your Email", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LogIn.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
