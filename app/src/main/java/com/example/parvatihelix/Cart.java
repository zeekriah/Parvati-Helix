package com.example.parvatihelix;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class Cart extends AppCompatActivity {
ListView lv;
FirebaseUser user;
FirebaseFirestore db;
FirebaseStorage fs;
int imgcount=0;
    String img="";
    String name="";

public  static Vector cartItemsName;
    public static  Vector itemquantity;
    public static Vector itemprice;
  public static Vector itemimage;
  public static Vector itemsName;
        public static Vector cartItemsImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        db=FirebaseFirestore.getInstance();
        fs=FirebaseStorage.getInstance();
       itemimage=new Vector();
        itemsName=new Vector();
        cartItemsName=new Vector();
        cartItemsImg=new Vector();
        itemprice=new Vector();
        itemquantity=new Vector();

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
                    String v = m.get("image").toString();
                    int price=Integer.parseInt(m.get("Price").toString());
                    int quant=Integer.parseInt(m.get("Quantity").toString());

                    itemimage.add(v);
                    itemsName.add(docs.getId());
                    itemprice.add(price);
                    itemquantity.add(quant);

                    imgcount=imgcount+1;
                    img=img+" "+v;
                    name=name+"  "+docs.getId();


//                                            }

                }

//                                        Toast.makeText(Items.this, img, Toast.LENGTH_LONG).show();
//                Toast.makeText(Cart.this, itemimage.get(0).toString(), Toast.LENGTH_LONG).show();
                CartListAdapter adapter=new CartListAdapter(Cart.this,itemsName,itemimage,itemprice,itemquantity);
                lv=(ListView)findViewById(R.id.cartlist);
                lv.setAdapter(adapter);


//                cartItemsName=itemsName;
//               cartItemsImg=itemimage;
//                Toast.makeText(Cart.this, img, Toast.LENGTH_LONG).show();



            }
        });

//        Toast.makeText(Cart.this, str[1], Toast.LENGTH_LONG).show();


        //It will send name and images of items to adapter
//

    }
}
