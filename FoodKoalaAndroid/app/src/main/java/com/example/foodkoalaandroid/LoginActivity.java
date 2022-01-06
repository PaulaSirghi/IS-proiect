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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private Button b1;
    private FirebaseAuth mAuth;
    private String uid;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        b1=(Button)findViewById(R.id.login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }
    private void loginUser(){
        String em = email.getText().toString();
        String pa = pass.getText().toString();
        if(!em.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(em).matches()){
            if(!pa.isEmpty()){
               mAuth.signInWithEmailAndPassword(em,pa).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                   @Override
                   public void onSuccess(@NonNull AuthResult authResult) {
                       mAuth = FirebaseAuth.getInstance();
                       uid = (String)mAuth.getCurrentUser().getUid();
                       ref = FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(uid);
                       ref.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               if (dataSnapshot.getValue() == null) return;
                               User u = dataSnapshot.getValue(User.class);
                               String rol = u.getRol();
                               String n = u.getName();
                               if (rol.equals("client"))
                               {
                                   Toast.makeText(LoginActivity.this,"Login Successfully!", Toast.LENGTH_LONG).show();
                                   Intent i = new Intent(getBaseContext(), FoodActivity.class);
                                   Cart cart = new Cart();
                                   Order o = new Order();
                                   i.putExtra("Cart",  cart);
                                   i.putExtra("Order", o);
                                   i.putExtra("Client",u);
                                   startActivity(i);
                               }
                               else{
                                   if (rol.equals("administrator")){
                                       Intent i=new Intent(LoginActivity.this, Admin.class);
                                       startActivity(i);
                                   }
                                   else {
                                       if (rol.equals("livrator")){
                                           Intent i=new Intent(LoginActivity.this, LivratorActivity.class);
                                           i.putExtra("name", n);
                                           startActivity(i);
                                       }
                                       else
                                          Toast.makeText(LoginActivity.this, "Unrecognized user", Toast.LENGTH_SHORT).show();
                                   }
                             }
                           }
                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {
                               Toast.makeText(LoginActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                           }
                       });


                       System.out.println("Eroare");
                       finish();
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_LONG);
                       System.out.println("Eroare");
                   }
               });
            }
            else {
                pass.setError("Empty fields");
            }
        }
        else if(em.isEmpty()){
            email.setError("Empty fields");
        }
        else{
            email.setError("Enter correct email");
        }
    }
}