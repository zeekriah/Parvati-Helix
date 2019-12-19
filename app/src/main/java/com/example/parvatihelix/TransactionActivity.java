package com.example.parvatihelix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TransactionActivity extends AppCompatActivity {
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        findViewById(R.id.trans).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
//               Toast.makeText(TransactionActivity.this,"clicked" ,Toast.LENGTH_SHORT).show();
               b1=findViewById(R.id.trans);
                b1.setText("aaaaaa");
           }
       });
    }
}
