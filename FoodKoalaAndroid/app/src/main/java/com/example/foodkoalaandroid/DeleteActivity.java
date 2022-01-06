package com.example.foodkoalaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class DeleteActivity extends AppCompatActivity {
    private EditText email;
    private Button b1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        email = (EditText) findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();

        b1 = (Button) findViewById(R.id.delete);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }

    private void deleteUser() {
        String em = email.getText().toString();

        if (em.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
            email.setError("Email field is required");
            return;
        }
        FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").orderByChild("email").equalTo(em).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    startActivity(new Intent(DeleteActivity.this,Admin.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteActivity.this, "nu exista utilizator", Toast.LENGTH_LONG).show();
            }

        });


    }
}