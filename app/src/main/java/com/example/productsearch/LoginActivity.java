package com.example.productsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.productsearch.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText  UserName,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton=(Button) findViewById(R.id.login_button);
        UserName=(EditText) findViewById(R.id.username);
        Password=(EditText) findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });


    }

    private void LoginUser(){
        String email=UserName.getText().toString();
        String password=Password.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"write your email", Toast.LENGTH_LONG).show();
        }
        else  if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"write your password", Toast.LENGTH_LONG).show();
        }else{
            AllowAccessToYourAccount( email, password);
        }
    }
    private void AllowAccessToYourAccount(final String email, final String password){
        final DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(email).exists()){
                    Users users=dataSnapshot.child("Users").child(email).getValue(Users.class);
                    if(users.getEmail().equals(email)){
                        if(users.getPassword().equals(password)){
                            Intent intent=new Intent(LoginActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }else
                            Toast.makeText(LoginActivity.this, "incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
