package com.example.foodkoalaandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EditActivity extends AppCompatActivity {
    private Button b1;
    private DatabaseReference ref;
    private DatabaseReference ref2;
    private FirebaseAuth mAuth;
    private String uid;
    private EditText nume,email,parola,numar,adresa,cont;

    private final List<User> userList;

    {
        userList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        nume=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        parola=(EditText)findViewById(R.id.password);
        numar=(EditText)findViewById(R.id.phone);
        adresa=(EditText)findViewById(R.id.adresa);
        cont=(EditText)findViewById(R.id.cont);
        b1=(Button)findViewById(R.id.button1);

        mAuth = FirebaseAuth.getInstance();
        uid = (String)mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) return;

                User u = dataSnapshot.getValue(User.class);

                String numele = u.getName();
                nume.setText(numele);
                email.setText(u.getEmail());
                String email_s = u.getEmail();
                String pas_s = u.getPass();
                parola.setText(u.getPass());
                numar.setText(u.getNumar_telefon());
                ref2 = FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Clients").child(uid);
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            Toast.makeText(EditActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ClientT c = dataSnapshot.getValue(ClientT.class);
                        b1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(u.getEmail(),u.getPass());

                                ((FirebaseUser) user).reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            private String TAG;
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    user.updateEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(EditActivity.this,"Email updated",Toast.LENGTH_LONG).show();

                                                            }
                                                            else{
                                                                Toast.makeText(EditActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();

                                                            }
                                                        }
                                                    });

                                                    user.updatePassword(parola.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(EditActivity.this,"Password updated",Toast.LENGTH_LONG).show();
                                                            } else {
                                                                Toast.makeText(EditActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(EditActivity.this,"Something went wrong2",Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });
                                saveInfo(u,c);
                            }
                        });
                        adresa.setText(c.getAdresa());
                        cont.setText(c.getCont());

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(EditActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });


    }
    void saveInfo(User u, ClientT c){

        ref= FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(uid);
        ref.child("email").setValue(email.getText().toString());
        ref.child("name").setValue(nume.getText().toString());
        ref.child("pass").setValue(parola.getText().toString());
        ref.child("numar_telefon").setValue(numar.getText().toString());



        ref2= FirebaseDatabase.getInstance("https://foodkoala-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Clients").child(uid);
        ref2.child("adresa").setValue(adresa.getText().toString());
        ref2.child("cont").setValue(cont.getText().toString());



    }
}