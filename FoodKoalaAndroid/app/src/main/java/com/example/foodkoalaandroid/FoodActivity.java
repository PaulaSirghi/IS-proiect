package com.example.foodkoalaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class FoodActivity extends AppCompatActivity implements Observer{
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FloatingActionButton b1,b2,b3,b4;
    private ArrayList<Produs> f= new ArrayList<>();
    private ArrayList<String> produse = new ArrayList<>();
    private User client;
    private Order o;
    private Cart c;
    private String nume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        mAuth = FirebaseAuth.getInstance();
        b2=(FloatingActionButton)findViewById(R.id.fab2);
        b4=(FloatingActionButton)findViewById(R.id.exit);
        b3=(FloatingActionButton)findViewById(R.id.fab3);
        if(getIntent().getExtras() != null) {
            this.client= (User) getIntent().getSerializableExtra("Client");
            this.o = (Order) getIntent().getSerializableExtra("Order");
            this.c = (Cart) getIntent().getSerializableExtra("Cart");
            this.nume = client.getName();
            System.out.println(nume+" ESTE NUMELE");

        }


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FoodActivity.this,MenuActivity.class);
                i.putExtra("Cart",  c);
                i.putExtra("Order", o);
                i.putExtra("Client",client);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FoodActivity.this,EditActivity.class);
                startActivity(i);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i=new Intent(FoodActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void update(String mesaj) {
        Toast.makeText(FoodActivity.this,mesaj,Toast.LENGTH_LONG).show();
    }
}