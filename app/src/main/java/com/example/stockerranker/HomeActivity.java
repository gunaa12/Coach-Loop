package com.example.stockerranker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private void pushToCloud(String name, String school, String review, int wins, int losses, int rating, double avgPointWin) {
        try {
            db.collection("Coaches").document(name).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        String reviews = "";
                        long numOfReviews = 1;
                        if (document.exists()) {
                            reviews += (String) document.getData().get("reviews");
                            numOfReviews += (long) document.getData().get("numOfReviews");
                        }
                        Map<String, Object> coach = new HashMap<String, Object>();
                        coach.put("school", school);
                        coach.put("wins", wins);
                        coach.put("losses", losses);
                        coach.put("rating", rating);
                        coach.put("reviews", reviews + "; " + review);
                        coach.put("avgPointWin", avgPointWin);
                        coach.put("numOfReviews", numOfReviews);
                        db.collection("Coaches").document(name).set(coach);
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(HomeActivity.this, "Push to Cloud Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private int getMLRating() {
        Toast.makeText(HomeActivity.this, "In the ML function", Toast.LENGTH_SHORT).show();
        File file = new File("C://Users//gunav//Desktop//StockerRanker//app//review_com_file.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp = "Rating: " + br.readLine();
            Toast.makeText(HomeActivity.this, temp, Toast.LENGTH_SHORT).show();
            br.close();
        } catch(Exception e) {
            Toast.makeText(HomeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();

        pushToCloud("hello", "idk2", "idk 2", 3, 6, 1, 3.4);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //instantiate logout button
        Button btnLogout = (Button) findViewById(R.id.btnLogout); //logout button
getMLRating();
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