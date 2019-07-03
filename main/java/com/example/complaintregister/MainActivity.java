package com.example.complaintregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText email,pass;
    Button btn;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button loginPage;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.emailSignUp);
        pass=findViewById(R.id.passSIgnUp);
        btn=findViewById(R.id.signUPBtn);
        loginPage=findViewById(R.id.logINpage);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        linearLayout=findViewById(R.id.img);
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (firebaseUser!=null && firebaseUser.isEmailVerified()){
            startActivity(new Intent(MainActivity.this,Profile.class));
            finish();
        }
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LogIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void signUp() {
        final String emailtext=email.getText().toString().trim();
        final String passtext=pass.getText().toString().trim();
        if (TextUtils.isEmpty(emailtext) && TextUtils.isEmpty(passtext)){
            Toast.makeText(this,"Both fields are empty",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailtext)){
            Toast.makeText(this,"Roll No field is empty",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(passtext)){
            Toast.makeText(this,"Password field is empty",Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.show();
            databaseReference= FirebaseDatabase.getInstance().getReference().child(emailtext+"/emailAddress");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String email=dataSnapshot.getValue().toString();
                    firebaseAuth.createUserWithEmailAndPassword(email,passtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                firebaseAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {

                                                                   @Override
                                                                   public void onComplete(@NonNull Task<Void> task) {
                                                                       if (task.isSuccessful()){
                                                                           Toast.makeText(MainActivity.this,"Register Successful. Please check your email for verification",Toast.LENGTH_SHORT).show();
                                                                           progressDialog.dismiss();
                                                                       }
                                                                       else {
                                                                           Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                           progressDialog.dismiss();
                                                                       }
                                                                   }
                                                               }
                                        );
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
                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.info){
            Thread thread=new Thread(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                linearLayout.setVisibility(View.GONE);
                            }
                        });
                    }catch (Exception e){}
                }
            };
            thread.start();
            linearLayout.setVisibility(View.VISIBLE);
        }
        return true;
    }
}
