package com.example.parvatihelix;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Vector;

public class TransactionListAdapter extends ArrayAdapter<String> {
   private final Activity context;
    private final Vector itemsName,itemsPrice,itemsQuant,Pdate,Saddress;

    FirebaseFirestore db;
    FirebaseUser user;
    public TransactionListAdapter(Activity context, Vector itemsName, Vector itemsPrice, Vector itemsQuant,Vector Pdate,Vector Saddress) {
        super(context,R.layout.activity_transaction,itemsName);
        this.context = context;
        this.itemsName = itemsName;
        this.Pdate=Pdate;
        this.itemsPrice=itemsPrice;
        this.itemsQuant=itemsQuant;
        this.Saddress=Saddress;

    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_transaction_structure,null,true);
        TextView text=(TextView)rowView.findViewById(R.id.prod);
//        ImageView img=(ImageView)rowView.findViewById(R.id.cartitemimg);
//        AutoCompleteTextView qty=rowView.findViewById(R.id.select);
        TextView price=rowView.findViewById(R.id.price);
        TextView date=rowView.findViewById(R.id.date);
        TextView totprice=rowView.findViewById(R.id.totprice);
        TextView qty=rowView.findViewById(R.id.qty);
        TextView address=rowView.findViewById(R.id.Address);




        db= FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        date.setText(Pdate.get(position).toString());
        text.setText(itemsName.get(position).toString());
//        img.setImageResource(itemsImg[position]);
        qty.setText(itemsQuant.get(position).toString());
        address.setText((Saddress.get(position).toString()));
        price.setText(itemsPrice.get(position).toString());
        totprice.setText(itemsPrice.get(position).toString());




        return rowView;

    }
}
