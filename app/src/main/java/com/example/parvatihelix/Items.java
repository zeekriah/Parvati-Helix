package com.example.parvatihelix;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

public class Items extends AppCompatActivity {
    DocumentReference df,ds;
    FirebaseFirestore db;
    FirebaseStorage fs;
    ArrayList<String> list;
    Map m;

    ListView lv;
    Context context;
    ArrayList   arrayList;

    public static Integer[] itemsImg={
            R.drawable.ic_calendar,R.drawable.ic_email,R.drawable.ic_location
    };

    int imgcount;
    public Vector itemimage;
    public static Vector itemsName;
    public static Vector itemprice;

    ItemsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        db=FirebaseFirestore.getInstance();
        fs=FirebaseStorage.getInstance();
        itemimage=new Vector();
        itemsName=new Vector();
        itemprice=new Vector();
        lv=(ListView)findViewById(R.id.list);
        this.setTitle("Products");


        imgcount=0;

//

//        list = new ArrayList<String>();
//        String Category = getIntent().getExtras().getString("Category");
//                            Toast.makeText(Items.this, Category,Toast.LENGTH_LONG).show();
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
//
        String catname=getIntent().getExtras().getString("Category");
        /** Defining a click event listener for the button "Add" */
        db.collection("Categories").document(catname).collection("SubCategories").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                        String img="";
                                        String name="";
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
                                            itemprice.add(price);

                                            itemimage.add(v);
                                                itemsName.add(docs.getId());

                                                imgcount=imgcount+1;
                                                img=img+" "+v;
                                                name=name+"  "+docs.getId();
//                                            }

                                        }
//                                        Toast.makeText(Items.this, img, Toast.LENGTH_LONG).show();
//                                        Toast.makeText(Items.this, name, Toast.LENGTH_LONG).show();

                                        //It will send name and images of items to adapter
                                        adapter=new ItemsListAdapter(Items.this,itemsName,itemimage,itemprice);
                                        lv.setAdapter(adapter);



                                        //end

                                    }
                                });



        //access the data from list
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(Items.this, productdetails.class);
                String pname =itemsName.get(position).toString();
                String pimage =itemimage.get(position).toString();
                String pprice=itemprice.get(position).toString();

                Toast.makeText(Items.this, pname, Toast.LENGTH_LONG).show();

                intent.putExtra("productname", pname);
                intent.putExtra("productimage", pimage);
                intent.putExtra("productprice",pprice);

                startActivity(intent);
            }
        });
        //end
//
////                EditText edit = (EditText) findViewById(R.id.txtItem);
////                edit.setText("");
//
//
//        /** Setting the event listener for the add button */
////        btn.setOnClickListener(listener);
//
//        /** Setting the adapter to the ListView */
//        setListAdapter(adapter);


    }
}
