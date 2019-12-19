package com.example.parvatihelix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FillDetails extends AppCompatActivity {
DocumentReference df;
FirebaseFirestore db;
ProgressDialog progressDialog;
String Mob;
EditText nameField,emailField,locationField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);
        db=FirebaseFirestore.getInstance();
        Mob = getIntent().getExtras().getString("mobile");
        df=db.collection("users").document(Mob);
    nameField=findViewById(R.id.fullName);
        emailField=findViewById(R.id.userEmailId);

        locationField=findViewById(R.id.location);

        findViewById(R.id.signUpBtn).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                progressDialog=new ProgressDialog(FillDetails.this);
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Registering You...");
                progressDialog.show();

                Map user=new HashMap();
                String name,email,location;

                name=nameField.getText().toString().trim();
                email=emailField.getText().toString().trim();
                location=locationField.getText().toString().trim();


                user.put("Name",name);
                user.put("Email",email);
                user.put("Location",location);
                df.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(FillDetails.this, MyNavigation.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(FillDetails.this, "db success",Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FillDetails.this, "db failed",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
}
