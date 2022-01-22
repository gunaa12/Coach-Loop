package com.example.stockerranker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.os.Bundle;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("helloooooooooooo");
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword("testemail@gmail.com", "testpass").addOnCompleteListener(task -> System.out.println("finished"));
    }
}