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

import com.example.parvatihelix.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Vector;

import static com.example.parvatihelix.R.layout.activity_itemstructure;

public class ItemsListAdapter extends ArrayAdapter<String>
{
    private final Activity context;
    private final Vector itemsName;
    private final Vector itemsImg;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(activity_itemstructure,null,true);
        TextView text=(TextView)rowView.findViewById(R.id.itemname);
        ImageView img=(ImageView)rowView.findViewById(R.id.itemimg);

        text.setText(itemsName.get(position).toString());
//        img.setImageResource(itemsImg[position]);
        Picasso.with(context).load(itemsImg.get(position).toString()).into(img);


        return rowView;
    }

    public ItemsListAdapter(Activity context, Vector itemsName, Vector itemsImg) {
        super(context, activity_itemstructure,itemsName);
        this.context = context;
        this.itemsName = itemsName;
        this.itemsImg = itemsImg;
    }

}
