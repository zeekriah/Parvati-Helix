package com.example.parvatihelix;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Vector;

import static com.example.parvatihelix.R.layout.activity_cart;
import static com.example.parvatihelix.R.layout.activity_itemstructure;
import static com.example.parvatihelix.R.layout.cart_fragment;

public class CartListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final Vector itemsName;
    private final Vector itemsImg;
    private final Vector itemsPrice;
    private final Vector itemsQuant;


    FirebaseFirestore db;
    FirebaseUser user;

    public CartListAdapter(Activity context, Vector itemsName, Vector itemsImg, Vector itemsPrice,Vector itemsQuant) {
        super(context,R.layout.activity_cart_structure,itemsName);
        this.context = context;
        this.itemsName = itemsName;
        this.itemsImg = itemsImg;
        this.itemsPrice=itemsPrice;
        this.itemsQuant=itemsQuant;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_cart_structure,null,true);
        TextView text=(TextView)rowView.findViewById(R.id.cartitemname);
        ImageView img=(ImageView)rowView.findViewById(R.id.cartitemimg);
        AutoCompleteTextView qty=rowView.findViewById(R.id.select);
        TextView price=rowView.findViewById(R.id.cartprice);


        db= FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();


        text.setText(itemsName.get(position).toString());
//        img.setImageResource(itemsImg[position]);
        qty.setText(itemsQuant.get(position).toString());
        price.setText(itemsPrice.get(position).toString());
        Picasso.with(context).load(itemsImg.get(position).toString()).into(img);

        Button deleteImageView = (Button) rowView.findViewById(R.id.delete);
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.collection("users").document(user.getPhoneNumber()).collection("carts").document(itemsName.get(position).toString()).delete();
//                Intent intent = context.getIntent();
                Intent intent = new Intent(context,MyNavigation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                context.startActivity(intent);
            }
        });
        return rowView;

    }
}
