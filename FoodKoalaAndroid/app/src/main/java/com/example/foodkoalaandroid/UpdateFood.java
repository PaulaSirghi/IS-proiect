package com.example.foodkoalaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateFood extends AppCompatActivity {
    private EditText name;
    private EditText des;
    private EditText img;
    private EditText pret;
    private Button b1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        name=(EditText) findViewById(R.id.name);
        des=(EditText) findViewById(R.id.des);
        img=(EditText) findViewById(R.id.img);
        pret=(EditText) findViewById(R.id.pret);
        mAuth = FirebaseAuth.getInstance();

        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFood();
            }
        });
    }

    private void updateFood(){

        String na=name.getText().toString();
        String d = des.getText().toString();
        String i = img.getText().toString();
        int p=Integer.parseInt(pret.getText().toString());

        if(na.isEmpty())
        {
            name.setError("Name is required");
            return;
        }
        FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Food").orderByChild("name").equalTo(na).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                    String s=appleSnapshot.getKey();
                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Food").child(s).child("img").setValue(i);
                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Food").child(s).child("des").setValue(d);
                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Food").child(s).child("price").setValue(p);
                    startActivity(new Intent(UpdateFood.this,Admin.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFood.this, "nu exista mancare", Toast.LENGTH_LONG).show();
            }

        });

    }


}