package com.example.foodkoalaandroid;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewUsers<arrayAdapter> extends AppCompatActivity {
    private TextView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        lv = (TextView) findViewById(R.id.view);
        FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").orderByChild("name").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            List<String> users=new ArrayList<String>();
            for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                String u=appleSnapshot.child("name").getValue(String.class);
                users.add(u);
                System.out.println(u+" e numele");
            }
            for(int i=0; i < users.size(); i++)
            {   if(i<users.size()-1)
                  lv.setText(lv.getText() + users.get(i) + " \n");
               else
                  lv.setText(lv.getText() + users.get(i));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(ViewUsers.this, "nu exista user", Toast.LENGTH_LONG).show();
        }

    });
        //for(int i=0; i < users.size(); i++)
        //{ lv.setText(lv.getText() + users.get(i) + " , ");
}}