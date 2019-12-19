package com.example.parvatihelix.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.parvatihelix.Items;
import com.example.parvatihelix.Login;
import com.example.parvatihelix.MyNavigation;
import com.example.parvatihelix.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class HomeFragment extends Fragment {
    DocumentReference df,ds;
    FirebaseFirestore db;
    FirebaseStorage fs;
    boolean userfound=false;
    FirebaseUser user;
    View root;


    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);



                Toast.makeText(getActivity(), "bbbbxxx",Toast.LENGTH_LONG).show();
                db= FirebaseFirestore.getInstance();
                fs= FirebaseStorage.getInstance();
//        checkForValidUser();



                getCategories();


            }
        });
        return root;
    }
    void getCategories(){

        db.collection("Categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                int i=1;
                for(DocumentSnapshot ds: queryDocumentSnapshots){
                    Toast.makeText(getActivity(), "aaaaaaaaa",Toast.LENGTH_LONG).show();
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
                            "id",getActivity().getPackageName());
                    ImageButton imageView=root.findViewById(ressourceId);

                    int textid=getResources().getIdentifier("text"+i,"id",getActivity().getPackageName());
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), Items.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("Category",CategoryName);

                            startActivity(intent);
                        }
                    });
                    TextView textView=root.findViewById(textid);
                    Map m = ds.getData();
                    String v = m.get("image").toString();
                    Picasso.with(getActivity()).load(v).into(imageView);
                    textView.setText(ds.getId());
                    i++;

//                    }
                }
            }
        });

    }
    //end here
}