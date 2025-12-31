package com.example.to_dolist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText user, pass, confirmPass;
    Button register;
    TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new DatabaseHelper(this);
        user = findViewById(R.id.reg_username);
        pass = findViewById(R.id.reg_password);
        confirmPass = findViewById(R.id.reg_confirm_password);
        register = findViewById(R.id.btnRegister);
        backToLogin = findViewById(R.id.tvBackToLogin);

        register.setOnClickListener(v -> {
            String u = user.getText().toString();
            String p = pass.getText().toString();
            String cp = confirmPass.getText().toString();

            if (u.equals("") || p.equals("") || cp.equals("")) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (p.equals(cp)) {
                    boolean check = db.insertUser(u, p);
                    if (check) {
                        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Returns to Login screen
                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backToLogin.setOnClickListener(v -> finish());
    }
}