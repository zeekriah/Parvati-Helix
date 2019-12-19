package com.example.parvatihelix.ui.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.parvatihelix.Cart;
import com.example.parvatihelix.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.activity_transaction, container, false);
        final TextView textView = root.findViewById(R.id.transaction);
        final Button b1 = root.findViewById(R.id.trans);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setText("sssssssssssss");
                Toast.makeText(getActivity(), "bbbb",Toast.LENGTH_LONG).show();

            }
        });
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        Intent intent = new Intent(getActivity(), Cart.class);
//
//
                startActivity(intent);

        return root;
    }
}