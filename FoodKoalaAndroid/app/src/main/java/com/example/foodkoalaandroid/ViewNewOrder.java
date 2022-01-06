package com.example.foodkoalaandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewNewOrder extends AppCompatActivity {
    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_new_order);

        b1 = (Button)findViewById(R.id.b1);
        b2 = (Button)findViewById(R.id.b2);
        b3 = (Button)findViewById(R.id.b3);
        b4 = (Button)findViewById(R.id.b4);
        b5 = (Button)findViewById(R.id.b5);
        b6 = (Button)findViewById(R.id.b6);
        b7 = (Button)findViewById(R.id.b7);
        b8 = (Button)findViewById(R.id.b8);
        b9 = (Button)findViewById(R.id.b9);
        b10 = (Button)findViewById(R.id.b10);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        b4.setVisibility(View.INVISIBLE);
        b5.setVisibility(View.INVISIBLE);
        b6.setVisibility(View.INVISIBLE);
        b7.setVisibility(View.INVISIBLE);
        b8.setVisibility(View.INVISIBLE);
        b9.setVisibility(View.INVISIBLE);
        b10.setVisibility(View.INVISIBLE);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> users=new ArrayList<String>();
                List<String> ids = new ArrayList<>();
                List<Order> ords = new ArrayList<>();
                for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                    String u=appleSnapshot.child("nume_client").getValue(String.class);
                    String id = appleSnapshot.child("id_client").getValue(String.class);
                    String adresa = appleSnapshot.child("adresa").getValue(String.class);
                    int pret = appleSnapshot.child("pret").getValue(Integer.class);
                    ArrayList<Produs> produse = new ArrayList<>();
                    /*for(DataSnapshot s : appleSnapshot.child("produse").getChildren()){
                        String des = s.child("des").getValue(String.class);
                        String img = s.child("img").getValue(String.class);
                        String name = s.child("name").getValue(String.class);
                        int pret1;
                        pret1 = (int) s.child("price").getValue();
                        Produs p = new Produs(name,pret1,des,img);
                        produse.add(p);
                    }*/

                    String status = appleSnapshot.child("status").getValue(String.class);
                    Order order = new Order(pret,status,produse,u,adresa,id);
                    System.out.println(order.getNume_client()+" NUMELE");
                    ids.add(id);
                    ords.add(order);
                    users.add(u);

                }
                if (users.size()>0)
                {
                    b1.setVisibility(View.VISIBLE);
                    b1.setText(users.get(0));
                }
                if (users.size()>1)
                {
                    b2.setVisibility(View.VISIBLE);
                    b2.setText(users.get(1));
                }
                if (users.size()>2)
                {
                    b3.setVisibility(View.VISIBLE);
                    b3.setText(users.get(2));
                }
                if (users.size()>3)
                {
                    b4.setVisibility(View.VISIBLE);
                    b4.setText(users.get(3));
                }
                if (users.size()>4)
                {
                    b5.setVisibility(View.VISIBLE);
                    b5.setText(users.get(4));
                }
                if (users.size()>5)
                {
                    b6.setVisibility(View.VISIBLE);
                    b6.setText(users.get(5));
                }
                if (users.size()>6)
                {
                    b7.setVisibility(View.VISIBLE);
                    b7.setText(users.get(6));
                }
                if (users.size()>7)
                {
                    b8.setVisibility(View.VISIBLE);
                    b8.setText(users.get(7));
                }
                if (users.size()>8)
                {
                    b9.setVisibility(View.VISIBLE);
                    b9.setText(users.get(8));
                }
                if (users.size()>9)
                {
                    b10.setVisibility(View.VISIBLE);
                    b10.setText(users.get(9));
                }
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = (String)mAuth.getCurrentUser().getUid();
                DatabaseReference ref2 = FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(uid);
                //get the curent user
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) return;

                        User u = dataSnapshot.getValue(User.class);
                        String numele = u.getName();
                        if(ords.size()>0)
                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(0).setNume_livrator(numele);
                                    ords.get(0).setId_livrator(uid);
                                    ords.get(0).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(0)).setValue(ords.get(0));
                                    b1.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });
                        if(ords.size()>1)
                            b2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(1).setNume_livrator(numele);
                                    ords.get(1).setId_livrator(uid);
                                    ords.get(1).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(1)).setValue(ords.get(1));
                                    b2.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });
                        if(ords.size()>2)
                            b3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(2).setNume_livrator(numele);
                                    ords.get(2).setId_livrator(uid);
                                    ords.get(2).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(2)).setValue(ords.get(2));
                                    b3.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });
                        if(ords.size()>3)
                            b4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(3).setNume_livrator(numele);
                                    ords.get(3).setId_livrator(uid);
                                    ords.get(3).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(3)).setValue(ords.get(3));
                                    b4.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });
                        if(ords.size()>4)
                            b5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(4).setNume_livrator(numele);
                                    ords.get(4).setId_livrator(uid);
                                    ords.get(4).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(4)).setValue(ords.get(4));
                                    b5.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });
                        if(ords.size()>5)
                            b6.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(5).setNume_livrator(numele);
                                    ords.get(5).setId_livrator(uid);
                                    ords.get(5).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(5)).setValue(ords.get(5));
                                    b6.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });
                        if(ords.size()>6)
                            b7.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(6).setNume_livrator(numele);
                                    ords.get(6).setId_livrator(uid);
                                    ords.get(6).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(6)).setValue(ords.get(6));
                                    b7.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });
                        if(ords.size()>7)
                            b8.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(7).setNume_livrator(numele);
                                    ords.get(7).setId_livrator(uid);
                                    ords.get(7).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(7)).setValue(ords.get(7));
                                    b8.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });
                        if(ords.size()>8)
                            b9.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(8).setNume_livrator(numele);
                                    ords.get(8).setId_livrator(uid);
                                    ords.get(8).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(8)).setValue(ords.get(8));
                                    b9.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });
                        if(ords.size()>9)
                            b10.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ords.get(9).setNume_livrator(numele);
                                    ords.get(9).setId_livrator(uid);
                                    ords.get(9).setStatus("preluata");
                                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(ids.get(9)).setValue(ords.get(9));
                                    b10.setVisibility(View.INVISIBLE);
                                    ObserverClient oc = new ObserverClient();
                                    oc.update("Comanda preluata de "+numele);
                                }
                            });

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ViewNewOrder.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewNewOrder.this, "nu exista comenzi", Toast.LENGTH_LONG).show();
            }

        });
    }
}