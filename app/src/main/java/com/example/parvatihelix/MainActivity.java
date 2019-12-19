package com.example.parvatihelix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText mob,otp;
    FirebaseAuth firebaseAuth;
    String codeSent;
    Button sendOtp;
String number;
    String verificationId;
    int userfound=0;
    FirebaseFirestore db;
    FirebaseStorage fs;
    FirebaseUser user;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=FirebaseFirestore.getInstance();
        fs=FirebaseStorage.getInstance();

//        spinner = findViewById(R.id.spinnerCountries);
//        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        mob = findViewById(R.id.MobieNo);
//        otp = findViewById(R.id.GetOtp);
        sendOtp=findViewById(R.id.SendOtp);
//        otp.setEnabled(false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
//            user = FirebaseAuth.getInstance().getCurrentUser();
//        Toast.makeText(MainPage.this, "a"+user.getPhoneNumber(),Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, "bbbbbbb",Toast.LENGTH_LONG).show();

            db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    int i = 1;
                    for (DocumentSnapshot ds : queryDocumentSnapshots) {
                        Toast.makeText(MainActivity.this, "bbbbbbb",Toast.LENGTH_LONG).show();

                        if(user.getPhoneNumber().equalsIgnoreCase(ds.getId())){

                            Toast.makeText(MainActivity.this, "done",Toast.LENGTH_LONG).show();
//                        Toast.makeText(MainPage.this, "b"+ds.getId(),Toast.LENGTH_LONG).show();
                            userfound=1;
                            Intent intent = new Intent(MainActivity.this, MyNavigation.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
//                            Toast.makeText(MainActivity.this, user.getPhoneNumber(), Toast.LENGTH_LONG).show();

                            return;


                        }
//                        {
//                        else
//                            userfound=0;
//                        }

                    }
                    if(userfound==0){
                        Toast.makeText(MainActivity.this, "aaaaaaa", Toast.LENGTH_LONG).show();

                        FirebaseAuth.getInstance().signOut();
                        user.delete();
                    }
                }
            });



        }


        findViewById(R.id.SendOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                number = mob.getText().toString().trim();

                if (number.isEmpty() || number.length() !=  10) {
                    mob.setError("Valid number is required");
                    mob.requestFocus();
                    return;
                }

//                public boolean isNetworkAvailable(Context cont) {
//                    ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
//                    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
//                }
                    if(!haveNetwork()){
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                        return;
                    }
                number = "+91"+ number;
                sendVerificationCode(number);




            }
        });






    }
    private boolean haveNetwork() {
        boolean has_wifi = false;
        boolean has_mobile_data = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos= connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info: networkInfos){
            if(info.getTypeName().equalsIgnoreCase("Wifi")){
                if(info.isConnected()){
                    has_wifi=true;
                }
            }
            if(info.getTypeName().equalsIgnoreCase("Mobile")){
                if(info.isConnected()){
                    has_mobile_data=true;
                }
            }
        }
        return has_wifi || has_mobile_data;
    }



   public void sendVerificationCode(String number){
       progressDialog=new ProgressDialog(MainActivity.this);
       progressDialog.setTitle("Please wait");
       progressDialog.setMessage("Sending OTP");
       progressDialog.show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                MainActivity.this,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(MainActivity.this, "OTP Sent Successfully",Toast.LENGTH_LONG).show();

            verificationId = s;
            mob.setText("");
            Intent intent = new Intent(MainActivity.this,Login.class);
            intent.putExtra("mobile",number);
            intent.putExtra("OTP",verificationId);
            startActivity(intent);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }


        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
//            Toast.makeText(MainActivity.this, ,Toast.LENGTH_LONG).show();

//            if (code != null) {
////                progressBar.setVisibility(View.VISIBLE);
//                verifyCode(code);
//            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(MainActivity.this,"Something went wrong please try again later" ,Toast.LENGTH_LONG).show();

        }
    };

}
