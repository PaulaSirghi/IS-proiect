package com.example.foodkoalaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ListView l;
    private ArrayList<String> name  = new ArrayList<>();
    private DatabaseReference ref;
    private ArrayAdapter arrayAdapter;
    private ArrayList<Produs> p = new ArrayList<>();
    private Order o;
    private User client;
    private Cart c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        if(getIntent().getExtras() != null) {
            this.client= (User) getIntent().getSerializableExtra("Client");
        }
        if(getIntent().getExtras() != null)
            this.o = (Order) getIntent().getSerializableExtra("Order");
        if(getIntent().getExtras() != null)
            this.c = (Cart) getIntent().getSerializableExtra("Cart");

        ref = FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Food");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    if (child.getValue(Produs.class) == null) {
                        Toast.makeText(MenuActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        String id =  child.getKey();
                        Produs pr = child.getValue(Produs.class);
                        System.out.println(pr.getName());
                        name.add(pr.getName());
                        p.add(pr);

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        l = (ListView)findViewById(R.id.listV);
        arrayAdapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, name);
        l.setAdapter(arrayAdapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(getBaseContext(), FoodSelect.class);
                    i.putExtra("Cart",  c);
                    i.putExtra("Order", o);
                    i.putExtra("Client", client);
                    i.putExtra("Nume",l.getItemAtPosition(position).toString());
                    startActivity(i);
            }
        } );

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem m = menu.findItem(R.id.search);
        SearchView s = (SearchView) MenuItemCompat.getActionView(m);
        s.setQueryHint("Type here to search");
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}