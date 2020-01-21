package com.example.parvatihelix.ui.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.parvatihelix.Cart;
import com.example.parvatihelix.CartListAdapter;
import com.example.parvatihelix.R;
import com.example.parvatihelix.TransactionListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;
import java.util.Vector;

public class SlideshowFragment extends Fragment {

    ListView lv;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage fs;
    int imgcount=0;
    String img="";
    String name="";

    public  static Vector cartItemsName;

    public static Vector itemimage;
    public static  Vector itemquantity;
    public static Vector itemprice;
    public static Vector itemsName;
    public static Vector cartItemsImg;
    public static Vector purchasedate;
    public static  Vector SAddress;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        final View root = inflater.inflate(R.layout.activity_transaction, container, false);
//        final TextView textView = root.findViewById(R.id.transaction);


//        Button t=root.findViewById(R.id.trans);
//        t.setText("sxzjbxz");
        db= FirebaseFirestore.getInstance();
        fs= FirebaseStorage.getInstance();
        itemimage=new Vector();
        itemsName=new Vector();
        itemprice=new Vector();
        itemquantity=new Vector();
        purchasedate=new Vector();
        SAddress=new Vector();

        cartItemsName=new Vector();
        cartItemsImg=new Vector();
        user= FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(user.getPhoneNumber()).collection("transactions").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentSnapshot docs: queryDocumentSnapshots) {
//                                            TextView t=(TextView)findViewById(R.id.aaa);

//                                            t.setText(docs.getData().toString());
//                                            m = docs.getData();
//                                            list.add(ds.getId());
//                                            adapter.notifyDataSetChanged();
//                                            if(docs.getId()=="Hackshaw") {
//                                                Toast.makeText(Items.this, docs.getData().toString(), Toast.LENGTH_LONG).show();
                    Map m = docs.getData();
                    String imglink = m.get("PImage").toString();
                    String price="";
                    if(m.get("Price")!=null) {
                        price=m.get("Price").toString();
                    }

                    String quant=m.get("Quantity").toString();
                    String date=m.get("Date").toString();
                    String Address=m.get("Address").toString();

                    itemimage.add(imglink);
                    itemsName.add(docs.getId());
                    itemprice.add(price);
                    itemquantity.add(quant);
                    purchasedate.add(date);
                    SAddress.add(Address);



                    imgcount=imgcount+1;
                    img=img+" "+imglink;
                    name=name+"  "+docs.getId();


//                                            }

                }
             Toast.makeText(getActivity(),name, Toast.LENGTH_LONG).show();
                TransactionListAdapter adapter=new TransactionListAdapter(getActivity(),itemsName,itemprice,itemquantity,purchasedate,SAddress);
                lv=root.findViewById(R.id.transactionlist);
                lv.setAdapter(adapter);


            }
        });

        return root;
    }
}