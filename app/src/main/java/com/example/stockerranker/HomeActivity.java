package com.example.stockerranker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

    private void pushToCloud(String name, String school, String review, int wins, int losses, int rating, float avgPointWin) {
        try {
            Map<String, Object> coach = new HashMap<String, Object>();
            coach.put("school", school);
            coach.put("wins", wins);
            coach.put("losses", losses);
            coach.put("rating", rating);
            coach.put("review", review);
            coach.put("avgPointWin", avgPointWin);
            db.collection("Coaches").document(name).set(coach);
        } catch (Exception e) {
            Toast.makeText(HomeActivity.this, "Push to Cloud Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private int getMLRating(String review) throws IOException {
        File file = new File("../../../../../ml/model2/rating_com_file.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        System.out.println(line);
        br.close();
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();

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