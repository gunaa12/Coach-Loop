package com.example.stockerranker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates a new button object in java
        Button btnLogin = (Button)findViewById(R.id.btnLogin); //login button
        Button btnCreate = (Button)findViewById(R.id.btnCreate); //create account button

        /* This hunk of code changes to LoginActivity and passes a value that determines the
        visibility of some buttons and textViews in loginActivity
        */

        //changes to LoginActivity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sends LoginActivity a value of "login" (textViews in LoginActivity will be adjusted accordingly
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.putExtra("key","login");
                startActivity(i);
            }
        });

        //changes to LoginActivity
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sends login activity a different value
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.putExtra("key","create");
                startActivity(i);
            }
        });
    }
}