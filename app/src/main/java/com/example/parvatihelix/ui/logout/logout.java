package com.example.parvatihelix.ui.logout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parvatihelix.FillDetails;
import com.example.parvatihelix.MainActivity;
import com.example.parvatihelix.MyNavigation;
import com.example.parvatihelix.R;
import com.example.parvatihelix.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logout extends Fragment {

    private LogoutViewModel mViewModel;
    View root;


    public static logout newInstance() {
        return new logout();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.logout_fragment, container, false);
        mViewModel =
                ViewModelProviders.of(this).get(LogoutViewModel.class);
        root = inflater.inflate(R.layout.logout_fragment, container, false);
        final TextView textView = root.findViewById(R.id.l1);
        mViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

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
