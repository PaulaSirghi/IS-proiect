package com.example.foodkoalaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {
    private Button b1;
    private TextView t1,t2;
    private Order o;
    private User client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        if(getIntent().getExtras() != null)
            this.o = (Order) getIntent().getSerializableExtra("Order");
        if(getIntent().getExtras() != null) {
            this.client= (User) getIntent().getSerializableExtra("Client");
        }
        b1=(Button)findViewById(R.id.b1);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t1.setText(t1.getText()+" "+o.getStatus());
        if(o.getNume_livrator()!=null)
            t2.setText(t2.getText()+" "+o.getNume_livrator());
        else
            t2.setText(t2.getText()+" inca nu exista livrator");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(OrderActivity.this,CartActivity.class);
                i.putExtra("Order", o);
                i.putExtra("Cart",new Cart());
                i.putExtra("Client",client);
                startActivity(i);
            }
        });
    }
}