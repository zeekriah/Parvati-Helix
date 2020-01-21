package com.example.parvatihelix;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ShippingAddress extends AppCompatActivity implements PaymentResultListener {
    Button buy;
    String productname,productprice,productimage,quant;
    DocumentReference df;
    FirebaseFirestore db;
    FirebaseUser user;
    EditText rnno,area,city,state,pincode;
    String Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        buy=findViewById(R.id.buy);
        productname = getIntent().getExtras().getString("productname");
        productimage = getIntent().getExtras().getString("productimage");
        productprice = getIntent().getExtras().getString("productprice");
        quant   = getIntent().getExtras().getString("productquant");

        rnno=findViewById(R.id.rnno);
        area=findViewById(R.id.area);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        pincode =findViewById(R.id.pincode);

        db=FirebaseFirestore.getInstance();
        Address="";
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Address =rnno.getText().toString()+", "+area.getText().toString()+",\n"+city.getText().toString()+", "+state.getText().toString()+" - "+pincode.getText().toString();
//
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
                final Activity activity = ShippingAddress.this;

                final Checkout co = new Checkout();

                try {
                    JSONObject options = new JSONObject();
                    options.put("name", "Razorpay Corp");
                    options.put("description", "Demoing Charges");
                    //You can omit the image option to fetch the image from dashboard
                    options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
                    options.put("currency", "INR");


                    options.put("amount", (Integer.parseInt(productprice)*100));

                    JSONObject preFill = new JSONObject();
                    preFill.put("email", "khannuman675@gmail.com");
                    preFill.put("contact", "9967821385");

                    options.put("prefill", preFill);

                    co.open(activity, options);
                } catch (Exception e) {
                    Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(ShippingAddress.this, "success",Toast.LENGTH_LONG).show();
        user= FirebaseAuth.getInstance().getCurrentUser();


        df=db.collection("users").document(user.getPhoneNumber()).collection("transactions").document(productname);
//        String quant=qty.getText().toString().trim();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        Map usercart=new HashMap();
        usercart.put("PName",productname);
        usercart.put("PImage",productimage);
        usercart.put("Quantity",quant);
        usercart.put("Price",productprice);
        usercart.put("Date",thisDate);
        usercart.put("Address",Address);
        df.set(usercart).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ShippingAddress.this, "Successfully purchased",Toast.LENGTH_LONG).show();


            }
        });

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(ShippingAddress.this , s,Toast.LENGTH_LONG).show();


    }
}
