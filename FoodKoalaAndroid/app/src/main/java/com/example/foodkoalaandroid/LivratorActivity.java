package com.example.foodkoalaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LivratorActivity extends AppCompatActivity implements Observer {
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livrator);

        b1=(Button)findViewById(R.id.button12);
        b2=(Button)findViewById(R.id.button13);
        b3=(Button)findViewById(R.id.button14);
        b4=(Button)findViewById(R.id.button15);
        b5=(Button)findViewById(R.id.button16);

        Intent i= getIntent();
        name= i.getStringExtra("name");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LivratorActivity.this, ViewOrder.class);
                i.putExtra("name",name);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LivratorActivity.this, ViewNewOrder.class);
                i.putExtra("name",name);
                startActivity(i);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            Intent i=new Intent(LivratorActivity.this, DeleteOrder.class);
            startActivity(i);
        }
    });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LivratorActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LivratorActivity.this, UpdateStatus.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void update(String mesaj) {
        //Toast.makeText(LivratorActivity.this,"New order",Toast.LENGTH_LONG).show();
        Toast.makeText(LivratorActivity.this,mesaj,Toast.LENGTH_LONG).show();
    }
}