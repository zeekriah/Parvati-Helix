package com.example.parvatihelix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

public class MainPage extends AppCompatActivity {
    DocumentReference df,ds;
    FirebaseFirestore db;
    FirebaseStorage fs;
    boolean userfound=false;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        db=FirebaseFirestore.getInstance();
        fs=FirebaseStorage.getInstance();
//        checkForValidUser();

        getCategories();

//

    }
    void checkForValidUser(){
//        Toast.makeText(MainPage.this, "aaaaaa",Toast.LENGTH_LONG).show();
        userfound=false;
        user = FirebaseAuth.getInstance().getCurrentUser();
//        Toast.makeText(MainPage.this, "a"+user.getPhoneNumber(),Toast.LENGTH_LONG).show();

        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                int i = 1;
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                                            Toast.makeText(MainPage.this, "b"+ds.getId(),Toast.LENGTH_LONG).show();

                    if(user.getPhoneNumber().equalsIgnoreCase(ds.getId())){

                        Toast.makeText(MainPage.this, "done",Toast.LENGTH_LONG).show();
//                        Toast.makeText(MainPage.this, "b"+ds.getId(),Toast.LENGTH_LONG).show();
                        userfound=true;
                        return;

                    }
                    else{
                        userfound=false;

                    }
                }
            }
        });
//        if(userfound==false){
//        Intent intent = new Intent(MainPage.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);}

    }

    void getCategories(){
        db.collection("Categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                int i=1;
                for(DocumentSnapshot ds: queryDocumentSnapshots){
//                    Toast.makeText(MainPage.this, ds.toString(),Toast.LENGTH_LONG).show();
                    final String CategoryName=ds.getId();
//                    if (ds.getId().equalsIgnoreCase("Hackshaw")) {
                    String s="R.id.img"+i;
                    //
//                        DocumentReference document =
//                                db.collection("Categories").document("Hackshaw").collection("SubCategories").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                                        for(DocumentSnapshot docs: queryDocumentSnapshots) {
//                                            TextView t=(TextView)findViewById(R.id.aaa);
//
//                                            t.setText(docs.getData().toString());
//
//                                        }
//                                        }
//                                });
                                //

                        int ressourceId = getResources().getIdentifier(
                            "img"+i,
                            "id",MainPage.this.getPackageName());
                    ImageButton imageView=findViewById(ressourceId);
                    int textid=getResources().getIdentifier("text"+i,"id",MainPage.this.getPackageName());
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainPage.this, Items.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("Category",CategoryName);

                            startActivity(intent);
                        }
                    });
                    TextView textView=findViewById(textid);
                    Map m = ds.getData();
                    String v = m.get("image").toString();
                    Picasso.with(MainPage.this).load(v).into(imageView);
                    textView.setText(ds.getId());
                    i++;

//                    }
                }
            }
        });

    }


}
