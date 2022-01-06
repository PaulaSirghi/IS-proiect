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

public class UpdateActivity extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private EditText name;
    private EditText phone;
    private EditText rol;
    private Button b1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.password);
        name=(EditText) findViewById(R.id.name);
        phone=(EditText) findViewById(R.id.phone);
        rol=(EditText) findViewById(R.id.rol);

        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFood();
            }
        });
    }

    private void updateFood(){
        String em = email.getText().toString();
        String pa = pass.getText().toString();
        String nu=phone.getText().toString();
        String na=name.getText().toString();
        String r=rol.getText().toString();

        if(na.isEmpty())
        {
            name.setError("Name is required");
            return;
        }
        FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").orderByChild("email").equalTo(em).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                    String s=appleSnapshot.getKey();
                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(s).child("name").setValue(na);
                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(s).child("numar_telefon").setValue(nu);
                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(s).child("pass").setValue(pa);
                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(s).child("rol").setValue(r);
                    startActivity(new Intent(UpdateActivity.this,Admin.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateActivity.this, "nu exista user", Toast.LENGTH_LONG).show();
            }

        });

    }


}