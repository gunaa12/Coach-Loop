package com.example.stockerranker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        Map<String, Object> coach = new HashMap<String, Object>();
        coach.put("name", "test");
        coach.put("school", "test");
        coach.put("wins", 3);
        coach.put("losses", 2);
        coach.put("rating", 1);
        coach.put("review", "test");
        coach.put("avgPointWin", 1.1);
        db.collection("Coaches").add(coach);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //instantiate logout button
        Button btnLogout = (Button) findViewById(R.id.btnLogout); //logout button

        //logs the user out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });
    }
}