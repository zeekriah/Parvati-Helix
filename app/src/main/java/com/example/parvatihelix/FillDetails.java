package com.example.parvatihelix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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
CheckBox cb;
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
        cb=findViewById(R.id.terms_conditions);

        findViewById(R.id.signUpBtn).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                Map user=new HashMap();
                String name,email,location;

                name=nameField.getText().toString().trim();
                email=emailField.getText().toString().trim();
                location=locationField.getText().toString().trim();
                if(namevalidate(name)){
                    if(EmailValidate(email)){
                        if(checkbox(cb)){
                            progressDialog=new ProgressDialog(FillDetails.this);
                            progressDialog.setTitle("Please wait");
                            progressDialog.setMessage("Registering You...");
                            progressDialog.show();
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

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(FillDetails.this, "Something went wrong",Toast.LENGTH_LONG).show();

                                }
                            });

                        }
                    }
                }



            }
        });
    }
    Boolean  EmailValidate(String email)
    {

        if(email.matches("[a-zA-Z0-9._-]+@[a-z]*\\.[a-z]{2,3}"))
        {
            return true;

        }
        else   {

            Toast.makeText(FillDetails.this,"Invalid Email",Toast.LENGTH_SHORT).show();
            return false;
        }



    };
    Boolean namevalidate(String name)
    {
        if (name.matches("^[a-zA-Z\\s]+$")) {
                return  true;
        }
        else{
            Toast.makeText(FillDetails.this,"Invalid Name",Toast.LENGTH_SHORT).show();
            return false;
        }
    }



    Boolean checkbox(CheckBox checkbox)
    {
        final CheckBox checkBox = (CheckBox) findViewById(R.id.terms_conditions);
        if (checkBox.isChecked()) {

            checkBox.setChecked(false);
            return true;
        }
        else {
            Toast.makeText(FillDetails.this," Please Accept Terms & Condition ",Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
