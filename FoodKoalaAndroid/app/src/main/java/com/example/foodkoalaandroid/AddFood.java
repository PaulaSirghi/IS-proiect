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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class AddFood extends AppCompatActivity {
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
        setContentView(R.layout.activity_add_food);

        name=(EditText) findViewById(R.id.name);
        des=(EditText) findViewById(R.id.des);
        img=(EditText) findViewById(R.id.img);
        pret=(EditText) findViewById(R.id.pret);

        mAuth = FirebaseAuth.getInstance();

        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFood();
            }
        });
    }

    private void createFood(){
        String d = des.getText().toString();
        String i = img.getText().toString();
        int p=Integer.parseInt(pret.getText().toString());
        String na=name.getText().toString();
        if(d.isEmpty())
        {
            des.setError("Des is required");
            return;
        }
        if(i.isEmpty())
        {
            img.setError("Image is required");
            return;
        }

        if(na.isEmpty())
        {
            name.setError("Name is required");
            return;
        }
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        int length = 7;

        for(int j = 0; j < length; j++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        String id = new String(randomString);
        Food f=new Food(na,d,i,p);
        FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Food").child(randomString).setValue(f).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddFood.this,"add successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddFood.this,Admin.class));
                }
                else{
                    Toast.makeText(AddFood.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}