package com.example.parvatihelix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class productdetails extends AppCompatActivity {
TextView pname;
ImageView pimage;
Button cart,buy;
DocumentReference df;
FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        final String productname = getIntent().getExtras().getString("productname");
        final String productimage = getIntent().getExtras().getString("productimage");
        pname=findViewById(R.id.productname);
        pimage=findViewById(R.id.productimage);
        cart=findViewById(R.id.addtocart);

        db=FirebaseFirestore.getInstance();

        pname.setText(productname);
        Picasso.with(productdetails.this).load(productimage).into(pimage);

        //adding products to cart from this function
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(productdetails.this, Cart.class);


                startActivity(intent);
//                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
//
//                Toast.makeText(productdetails.this,user.getPhoneNumber(),Toast.LENGTH_LONG).show();
//                df=db.collection("users").document(user.getPhoneNumber()).collection("carts").document(productname);
//                EditText qty=findViewById(R.id.qty);
//                String quant=qty.getText().toString().trim();
//                Map usercart=new HashMap();
//                usercart.put("PName",productname);
//                usercart.put("PImage",productimage);
//                usercart.put("Quantity",quant);
//
//                df.set(usercart).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(productdetails.this, "Added To Cart",Toast.LENGTH_LONG).show();
//
//
//                    }
//                });


            }
        });


    }
}
