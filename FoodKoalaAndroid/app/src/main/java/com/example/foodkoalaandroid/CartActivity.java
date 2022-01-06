package com.example.foodkoalaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private Cart c;
    private Order o;
    private TextView t1;
    private Button b1,b2;
    private User client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if(getIntent().getExtras() != null) {
            this.client= (User) getIntent().getSerializableExtra("Client");
        }
        if(getIntent().getExtras() != null)
            this.o = (Order) getIntent().getSerializableExtra("Order");
        if(getIntent().getExtras() != null)
            this.c = (Cart) getIntent().getSerializableExtra("Cart");
        t1=(TextView)findViewById(R.id.t1);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        if(c!=null)
        {
            for(Produs p:c.getProduse()){
                t1.setText(t1.getText()+"\n"+ p.getName() + ": "+p.getPrice()+" lei");
            }
            t1.setText(t1.getText()+"\n"+"Pret total: "+c.getPret()+" lei");
        }
        else{
            t1.setText("Nu exista produse in cosul dumneavoastra");
        }
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = (String) mAuth.getCurrentUser().getUid();
                DatabaseReference ref2 = FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Clients").child(uid);
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            Toast.makeText(CartActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ClientT cl = dataSnapshot.getValue(ClientT.class);
                        String adresa;
                        String cont;
                        adresa=cl.getAdresa();
                        cont=cl.getCont();
                        if(adresa == null || cont == null)
                        {
                            Toast.makeText(CartActivity.this, "No address or card found", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            o = new Order(c.getPret(),"comanda plasata",c.getProduse(),client.getName(),adresa);
                            o.setId_client(uid);
                          /*  LivratorActivity a = new LivratorActivity();
                            FoodActivity f = new FoodActivity();
                            a.update("Comanda noua care poate fi preluata");
                            if(o.getNume_livrator()!=null)
                            {
                                f.update("Comanda preluata de livrator");
                            }*/
                            //the client and the delivery will receive the msg

                            FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(o).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(CartActivity.this,"Your order is on the way",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(CartActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            System.out.println("Comanda plasata "+o.getStatus()+ " "+o.getPret() + " "+ o.getAdresa()+" "+o.getNume_client());

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(CartActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            String uid = (String) mAuth.getCurrentUser().getUid();
            @Override
            public void onClick(View v) {
                if(o == null){
                    Toast.makeText(CartActivity.this,"Nu ati plasat nicio comanda",Toast.LENGTH_SHORT).show();
                }
                else{
                    //deschide pagina si vezi status comanda
                    Intent i=new Intent(CartActivity.this,OrderActivity.class);
                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Order").child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> users=new ArrayList<String>();
                            Order order = snapshot.getValue(Order.class);
                            System.out.println("ORDER "+order.getNume_client());
                            i.putExtra("Order", order);
                            i.putExtra("Client",client);
                            startActivity(i);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(CartActivity.this, "nu exista comenzi", Toast.LENGTH_LONG).show();
                        }

                    });

                }
            }
        });
    }
}