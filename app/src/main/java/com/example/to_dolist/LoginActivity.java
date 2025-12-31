package com.example.to_dolist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText user, pass;
    Button login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSignup);

        login.setOnClickListener(v -> {
            String userText = user.getText().toString().trim();
            String passText = pass.getText().toString().trim();

            // Reset errors
            user.setError(null);
            pass.setError(null);

            if (userText.isEmpty()) {
                user.setError("Username is required");
            } else if (passText.isEmpty()) {
                pass.setError("Password is required");
            } else {
                if (db.checkUser(userText, passText)) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    // Premium error for incorrect credentials
                    user.setError("Invalid credentials");
                    pass.setError("Please check your password");
                }
            }
        });

        // Inside LoginActivity.java onCreate
        signup.setOnClickListener(v -> {
            // This moves the user to the Sign Up page
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}