package com.example.udom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtEmail, edtPassword;
    private TextInputLayout layoutEmail, layoutPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Elements Connection
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPassword = findViewById(R.id.layoutPassword);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Login Button Click
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                String email = edtEmail.getText() != null ? edtEmail.getText().toString().trim() : "";
                String password = edtPassword.getText() != null ? edtPassword.getText().toString().trim() : "";

                if (layoutEmail != null) layoutEmail.setError(null);
                if (layoutPassword != null) layoutPassword.setError(null);

                if (email.isEmpty()) {
                    if (layoutEmail != null) layoutEmail.setError("Please enter your Username or Email");
                    return;
                }

                if (password.isEmpty()) {
                    if (layoutPassword != null) layoutPassword.setError("Please enter your password");
                    return;
                }

                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            });
        }

        // Register Link Click
        if (tvRegister != null) {
            tvRegister.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            });
        }

        // Forgot Password Link Click
        if (tvForgotPassword != null) {
            tvForgotPassword.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            });
        }
    }
}