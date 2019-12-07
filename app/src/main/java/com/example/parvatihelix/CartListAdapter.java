package com.example.parvatihelix;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.Vector;

import static com.example.parvatihelix.R.layout.activity_cart;
import static com.example.parvatihelix.R.layout.activity_itemstructure;

public class CartListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final Vector itemsName;
    private final Vector itemsImg;

    public CartListAdapter(Activity context, Vector itemsName, Vector itemsImg) {
        super(context,R.layout.activity_cart_structure,itemsName);
        this.context = context;
        this.itemsName = itemsName;
        this.itemsImg = itemsImg;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_cart_structure,null,true);
        TextView text=(TextView)rowView.findViewById(R.id.cartitemname);
        ImageView img=(ImageView)rowView.findViewById(R.id.cartitemimg);

        text.setText(itemsName.get(position).toString());
//        img.setImageResource(itemsImg[position]);
        Picasso.with(context).load(itemsImg.get(position).toString()).into(img);


        return rowView;

    }
}
