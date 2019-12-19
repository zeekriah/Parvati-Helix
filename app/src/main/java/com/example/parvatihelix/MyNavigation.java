package com.example.parvatihelix;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MyNavigation extends AppCompatActivity {
    DocumentReference df,ds;
    FirebaseFirestore db;
    FirebaseStorage fs;
    boolean userfound=false;
    FirebaseUser user;
    String UserName;
    TextView mNameTextView;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        user=FirebaseAuth.getInstance().getCurrentUser();
        db=FirebaseFirestore.getInstance();
        View header = navigationView.getHeaderView(0);


        mNameTextView = header.findViewById(R.id.name);


        df=db.collection("users").document(user.getPhoneNumber());
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        UserName=document.getString("Name");
                        mNameTextView.setText(UserName);

                    } else {
//                        Log.d("LOGGER", "No such document");
                    }
                } else {
//                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });


        TextView EmailTextView = header.findViewById(R.id.mobtextView);
        EmailTextView.setText(user.getPhoneNumber());


//        TextView te=(TextView)findViewById(R.id.name);
//        te.setText("fszcgvbvbjfhc");
       //from here
        db= FirebaseFirestore.getInstance();
        fs= FirebaseStorage.getInstance();
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
                    Toast.makeText(MyNavigation.this, "bbbb"+ds.getId(),Toast.LENGTH_LONG).show();

                    if(user.getPhoneNumber().equalsIgnoreCase(ds.getId())){

                        Toast.makeText(MyNavigation.this, "doneee",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MyNavigation.this, "aaaaaaaaa",Toast.LENGTH_LONG).show();
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
                            "id",MyNavigation.this.getPackageName());
                    ImageButton imageView=findViewById(ressourceId);
                    int textid=getResources().getIdentifier("text"+i,"id",MyNavigation.this.getPackageName());
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MyNavigation.this, Items.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("Category",CategoryName);

                            startActivity(intent);
                        }
                    });
                    TextView textView=findViewById(textid);
                    Map m = ds.getData();
                    String v = m.get("image").toString();
                    Picasso.with(MyNavigation.this).load(v).into(imageView);
                    textView.setText(ds.getId());
                    i++;

//                    }
                }
            }
        });

    }
    //end here


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_navigation, menu);
        return true;

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();



    }
}
