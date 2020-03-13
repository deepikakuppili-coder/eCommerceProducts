package com.example.productsearch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText InputName, InputUserName,InputPassword;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        createAccountButton =(Button) findViewById(R.id.signIn_button);
        InputName=(EditText) findViewById(R.id.signIn_name);
        InputUserName=(EditText) findViewById(R.id.signIn_username);
        InputPassword=(EditText) findViewById(R.id.signIn_password);
        progressBar=new ProgressBar(this);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });



    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void CreateAccount(){
        String name=InputName.getText().toString();
        String temp=InputUserName.getText().toString();
        String email=temp.substring(0,temp.length()-4);
        String password=InputPassword.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"write your name", Toast.LENGTH_LONG).show();
        }
        else  if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"write your email", Toast.LENGTH_LONG).show();
        }
        else  if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"write your passowrd", Toast.LENGTH_LONG).show();
        }

        else{
            validateUser(name,email,password);
        }


    }
    private void validateUser(final String name, final String email, final String password){
        final DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child("email").exists())){

                    HashMap<String,Object> userdataMap=new HashMap<>();
                    userdataMap.put("name",name);
                    userdataMap.put("email",email);
                    userdataMap.put("password",password);

                    reference.child("Users").child(email).updateChildren(userdataMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignInActivity.this,"you are registered successfully",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(SignInActivity.this,"network error",Toast.LENGTH_LONG).show();
                            }
                        }
                    });






                }else{
                    Toast.makeText(SignInActivity.this,"this email arleady exists",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
