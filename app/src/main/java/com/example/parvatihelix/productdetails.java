package com.example.parvatihelix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.StructuredQuery;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;


import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class productdetails extends AppCompatActivity implements PaymentResultListener {
TextView pname,pprice;
ImageView pimage;
Button cart,buy;
DocumentReference df;
FirebaseFirestore db;
EditText qty;
FirebaseUser user;
String price_acc_to_qty;
String productname,productprice,productimage,quant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
         productname = getIntent().getExtras().getString("productname");
        productimage = getIntent().getExtras().getString("productimage");
         productprice = getIntent().getExtras().getString("productprice");

        pname=findViewById(R.id.productname);
        pimage=findViewById(R.id.productimage);
        cart=findViewById(R.id.addtocart);
        buy=findViewById(R.id.buy);
        pprice=findViewById(R.id.productprice);
        price_acc_to_qty=productprice;

        db=FirebaseFirestore.getInstance();
        qty=findViewById(R.id.qty);
        qty.setText("1");
        pname.setText(productname);
        pprice.setText(productprice);
        Picasso.with(productdetails.this).load(productimage).into(pimage);
//        Razorpay razorpay = new Razorpay(activity, "YOUR_KEY_ID");

        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Toast.makeText(productdetails.this,qty.getText() , Toast.LENGTH_SHORT).show();

                if (qty.getText().toString().matches("")||qty.getText().toString()=="0"){
                    pprice.setText("0");

                }
                else {
                    int newprice=(Integer.parseInt(qty.getText().toString())*Integer.parseInt(productprice));

                    pprice.setText(Integer.toString(newprice));
                    price_acc_to_qty = Integer.toString(newprice);
                }


            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    JSONObject orderRequest = new JSONObject();
//                    orderRequest.put("amount", 50000); // amount in the smallest currency unit
//                    orderRequest.put("currency", "INR");
//                    orderRequest.put("receipt", "order_rcptid_11");
//                    orderRequest.put("payment_capture", false);
//
//                    StructuredQuery.Order order = razorpay.Orders.create(orderRequest);
//
////                } catch (RazorpayException e) {
////                    // Handle Exception
////                    System.out.println(e.getMessage());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                //from here

//                final Activity activity = productdetails.this;
//
//                final Checkout co = new Checkout();
//
//                try {
//                    JSONObject options = new JSONObject();
//                    options.put("name", "Razorpay Corp");
//                    options.put("description", "Demoing Charges");
//                    //You can omit the image option to fetch the image from dashboard
//                    options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
//                    options.put("currency", "INR");
//
//
//                    options.put("amount", (Integer.parseInt(price_acc_to_qty)*100));
//
//                    JSONObject preFill = new JSONObject();
//                    preFill.put("email", "khannuman675@gmail.com");
//                    preFill.put("contact", "9967821385");
//
//                    options.put("prefill", preFill);
//
//                    co.open(activity, options);
//                } catch (Exception e) {
//                    Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();

                //to here
                Intent intent = new Intent(productdetails.this, ShippingAddress.class);

                String quant=qty.getText().toString().trim();
                productprice=price_acc_to_qty;
                intent.putExtra("productname", productname);
                intent.putExtra("productimage", productimage);
                intent.putExtra("productprice",productprice);
                intent.putExtra("productquant",quant);


                startActivity(intent);

            }
        });

        //adding products to cart from this function
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(productdetails.this, Cart.class);
//
//
//                startActivity(intent);
                if(qty.getText().toString().trim()=="0" || qty.getText().equals("")){
                    Toast.makeText(productdetails.this,"Not a Valid Quantity",Toast.LENGTH_LONG).show();
                    return;


                }
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                Toast.makeText(productdetails.this,user.getPhoneNumber(),Toast.LENGTH_LONG).show();
                df=db.collection("users").document(user.getPhoneNumber()).collection("carts").document(productname);
                quant=qty.getText().toString().trim();


                Map usercart=new HashMap();
                usercart.put("PName",productname);
                usercart.put("PImage",productimage);
                usercart.put("Quantity",quant);
                usercart.put("Price",productprice);


                df.set(usercart).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(productdetails.this, "Added To Cart",Toast.LENGTH_LONG).show();


                    }
                });


            }
        });


    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(productdetails.this, "success",Toast.LENGTH_LONG).show();
        user= FirebaseAuth.getInstance().getCurrentUser();


        df=db.collection("users").document(user.getPhoneNumber()).collection("transactions").document(productname);
        String quant=qty.getText().toString().trim();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        Map usercart=new HashMap();
        usercart.put("PName",productname);
        usercart.put("PImage",productimage);
        usercart.put("Quantity",quant);
        usercart.put("Price",productprice);
        usercart.put("Date",thisDate);
        df.set(usercart).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(productdetails.this, "Successfully purchased",Toast.LENGTH_LONG).show();


            }
        });

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(productdetails.this, s,Toast.LENGTH_LONG).show();


    }
}
