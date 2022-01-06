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

public class UpdateStatus extends AppCompatActivity {
    private EditText name;
    private Button b1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        name=(EditText) findViewById(R.id.name);
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

        String p=(name.getText().toString());

        FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").orderByChild("adresa").equalTo(p).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                    String s=appleSnapshot.getKey();
                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Order").child(s).child("status").setValue("pe drum");
                    startActivity(new Intent(UpdateStatus.this,LivratorActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateStatus.this, "nu exista mancare", Toast.LENGTH_LONG).show();
            }

        });

    }


}