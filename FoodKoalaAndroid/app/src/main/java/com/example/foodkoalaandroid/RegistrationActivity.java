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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private EditText name;
    private EditText phone;
    private Button b1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.password);
        name=(EditText) findViewById(R.id.name);
        phone=(EditText) findViewById(R.id.phone);

        mAuth = FirebaseAuth.getInstance();

        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser(){
        String em = email.getText().toString();
        String pa = pass.getText().toString();
        String nu=phone.getText().toString();
        String na=name.getText().toString();
        if(em.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(em).matches())
        {
            email.setError("Email field is required");
            return;
        }
        if(pa.isEmpty())
        {
            pass.setError("Password is required");
            return;
        }
        if(nu.isEmpty() || nu.length()!=10)
        {
            phone.setError("Phone number is required and must have 10 characters");
            return;
        }
        if(na.isEmpty())
        {
            name.setError("Name is required");
            return;
        }
        mAuth.createUserWithEmailAndPassword(em,pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User u = new User(na,em,pa,nu,"client");

                    FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this,"registration successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegistrationActivity.this,MainActivityClient.class));
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegistrationActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
