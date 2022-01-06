package com.example.foodkoalaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivityClient extends AppCompatActivity {
    private Button b1;
    private EditText t1;
    private EditText t2;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        b1=(Button)findViewById(R.id.button1);
        t1=(EditText)findViewById(R.id.adresa);
        t2=(EditText)findViewById(R.id.cont);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String adresa = t1.getText().toString();
               String cont = t2.getText().toString();
               if(!adresa.isEmpty() && !cont.isEmpty())
               {
                   if(checkIban(cont)==true)
                   {
                       //create the new client
                       ClientT c = new ClientT(cont,adresa);

                       mAuth = FirebaseAuth.getInstance();
                       uid = (String)mAuth.getCurrentUser().getUid();
                       ref = FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(uid);


                       ref.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               if (dataSnapshot.getValue() == null) return;
                               FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Clients").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful()){
                                           Toast.makeText(MainActivityClient.this,"registration successfully",Toast.LENGTH_LONG).show();
                                           startActivity(new Intent(MainActivityClient.this,FoodActivity.class));
                                       }
                                       else{
                                           Toast.makeText(MainActivityClient.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                       }
                                   }
                               });

                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {
                               Toast.makeText(MainActivityClient.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                           }
                       });

                   }
                   else
                   {
                       Toast.makeText(MainActivityClient.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                       t2.setError("Cont introdus gresit");
                   }
               }
            }
        });
    }
    boolean checkIban(String cont){
        if (cont.length()==24){
            if(cont.charAt(0)=='R' && cont.charAt(1)=='O')
                return true;
            return false;
        }
        return false;
    }



}