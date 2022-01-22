package com.example.stockerranker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Check value of value";
    //firebase authentication variable
    private FirebaseAuth mAuth;

    //value taken from MainActivity
    String value = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //receive data from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        //create views
        TextView tvConfPassword = (TextView) findViewById(R.id.tvConfPassword); //confirm password textview
        TextView etConfPassword = (TextView) findViewById(R.id.etConfPassword); //Confirm password label
        Button btnLogin = (Button) findViewById(R.id.btnLogin); //login/create account button

        TextView etEmail = (TextView) findViewById(R.id.etEmail);
        TextView etPassword = (TextView) findViewById(R.id.etPassword);


        //change visibility if received value is "create"
        //change certain textViews if changed
        assert value != null;
        if (value.equals("create")) {
            tvConfPassword.setVisibility(View.VISIBLE);
            etConfPassword.setVisibility(View.VISIBLE);

            //change button text to Create Account
            String strText = "Create Account";
            btnLogin.setText(strText.toCharArray(), 0, strText.length());


        }

        //Create Account
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //create account
                    if (value.equals("create")) {
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString());
                        slow();

                        //sign in
                    } else if (value.equals("login")) {
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString());
                        slow();
                    }

                    //if login or create account is successful launch the homepage
                    if (mAuth.getCurrentUser() != null) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    void slow() {
        //delay the program
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}