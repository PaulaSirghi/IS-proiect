package com.example.foodkoalaandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodkoalaandroid.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FoodSelect extends AppCompatActivity {
    private TextView t1,t2,t3;
    private ImageView imageView;
    private FloatingActionButton b1, b2, b3;
    private User client;
    private Order o;
    private Cart c;
    private ArrayList<Produs> prod = new ArrayList<>();
    private String nume;
    private Uri imgn;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_select);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        imageView=(ImageView)findViewById(R.id.imageView);
        if(getIntent().getExtras() != null) {
            this.client= (User) getIntent().getSerializableExtra("Client");
        }
        if(getIntent().getExtras() != null)
            this.o = (Order) getIntent().getSerializableExtra("Order");
        if(getIntent().getExtras() != null)
           this.c = (Cart) getIntent().getSerializableExtra("Cart");
        if(getIntent().getExtras() != null)
            this.nume = (String) getIntent().getSerializableExtra("Nume");


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        DatabaseReference ref = FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Food");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    if (child.getValue(Produs.class) == null) {
                        Toast.makeText(FoodSelect.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        String id = child.getKey();
                        Produs pr = child.getValue(Produs.class);

                        if(pr.getName().equals(nume))
                        {
                            Picasso.get().load(pr.getImg()).into(imageView);
                            t2.setText(t2.getText()+" "+pr.getDes());
                            t3.setText(t3.getText()+" "+pr.getPrice());
                            b2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        c.addProdus(pr);
                                        System.out.println(pr.getName());
                                    }
                            });
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference(""+pr.getName()+".jpg");

                            try {
                                File loc = File.createTempFile("tempfile",".jpg");

                                storageReference.getFile(loc).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        System.out.println("her");
                                        Bitmap b = BitmapFactory.decodeFile(loc.getAbsolutePath());

                                        binding.imageView3.setImageBitmap(b);
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        b1=(FloatingActionButton)findViewById(R.id.b1);
        b2=(FloatingActionButton)findViewById(R.id.b2);
        b3=(FloatingActionButton)findViewById(R.id.b3);
        t1.setText(nume);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(FoodSelect.this, CartActivity.class);
                i.putExtra("Cart",c);
                i.putExtra("Order", o);
                i.putExtra("Client", client);
                startActivity(i);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FoodSelect.this,MenuActivity.class);
                i.putExtra("Cart",  c);
                i.putExtra("Order", o);
                i.putExtra("Client",client);
                startActivity(i);
            }
        });


    }
}