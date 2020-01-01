package com.example.parvatihelix.ui.cart;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parvatihelix.Cart;
import com.example.parvatihelix.CartListAdapter;
import com.example.parvatihelix.R;
import com.example.parvatihelix.ui.logout.LogoutViewModel;
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

public class cart extends Fragment {
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
    private LogoutViewModel mViewModel;
View root;
    public static cart newInstance() {
        return new cart();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel =
                ViewModelProviders.of(this).get(LogoutViewModel.class);
        root = inflater.inflate(R.layout.cart_fragment, container, false);
//        final TextView textView = root.findViewById(R.id.it);
        mViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                db= FirebaseFirestore.getInstance();
                fs= FirebaseStorage.getInstance();
                itemimage=new Vector();
                itemsName=new Vector();
                itemprice=new Vector();
                itemquantity=new Vector();

                cartItemsName=new Vector();
                cartItemsImg=new Vector();
                user= FirebaseAuth.getInstance().getCurrentUser();

                imgcount=0;

//

//        list = new ArrayList<String>();
//        String Category = getIntent().getExtras().getString("Category");
//                            Toast.makeText(Items.this, Category,Toast.LENGTH_LONG).show();
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

//
                /** Defining a click event listener for the button "Add" */
                db.collection("users").document(user.getPhoneNumber()).collection("carts").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                            itemimage.add(imglink);
                            itemsName.add(docs.getId());
                            itemprice.add(price);
                            itemquantity.add(quant);


                            imgcount=imgcount+1;
                            img=img+" "+imglink;
                            name=name+"  "+docs.getId();


//                                            }

                        }

//                                        Toast.makeText(Items.this, img, Toast.LENGTH_LONG).show();
//                Toast.makeText(Cart.this, itemimage.get(0).toString(), Toast.LENGTH_LONG).show();
                        CartListAdapter adapter=new CartListAdapter(getActivity(),itemsName,itemimage,itemprice,itemquantity);
                        lv=root.findViewById(R.id.cartlist);
                        lv.setAdapter(adapter);


//                cartItemsName=itemsName;
//               cartItemsImg=itemimage;
//                Toast.makeText(Cart.this, img, Toast.LENGTH_LONG).show();



                    }
                });

//        Toast.makeText(Cart.this, str[1], Toast.LENGTH_LONG).show();


                //It will send name and images of items to adapter
//
//                textView.setText(s);

            }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(LogoutViewModel.class);
//        // TODO: Use the ViewModel
//    }

        });
        return root;
    }
}


