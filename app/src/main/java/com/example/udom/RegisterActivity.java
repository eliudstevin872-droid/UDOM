package com.example.udom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText edtFullName, edtRegNo, edtEmail, edtPhone, edtPassword;
    private TextInputLayout layoutFullName, layoutRegNo, layoutEmail, layoutPhone, layoutPassword;
    private MaterialButton btnCreateAccount;
    private ImageView btnBack; // Kitufe cha kurudi nyuma

    // DatabaseHelper dbHelper; // Tumia hii kama unayo database helper yako

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // dbHelper = new DatabaseHelper(this); // Initialization ya Database

        // Views Initialization
        btnBack = findViewById(R.id.btnBack);
        layoutFullName = findViewById(R.id.layoutFullName);
        layoutRegNo = findViewById(R.id.layoutRegNo);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPhone = findViewById(R.id.layoutPhone);
        layoutPassword = findViewById(R.id.layoutPassword);

        edtFullName = findViewById(R.id.edtFullName);
        edtRegNo = findViewById(R.id.edtRegNo);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);

        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // Kazi ya Back Button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnCreateAccount.setOnClickListener(v -> {
            String fullName = edtFullName.getText() != null ? edtFullName.getText().toString().trim() : "";
            String regNo = edtRegNo.getText() != null ? edtRegNo.getText().toString().trim() : "";
            String email = edtEmail.getText() != null ? edtEmail.getText().toString().trim() : "";
            String phone = edtPhone.getText() != null ? edtPhone.getText().toString().trim() : "";
            String password = edtPassword.getText() != null ? edtPassword.getText().toString().trim() : "";

            // Reset errors
            layoutFullName.setError(null);
            layoutRegNo.setError(null);
            layoutEmail.setError(null);
            layoutPhone.setError(null);
            layoutPassword.setError(null);

            if (fullName.isEmpty()) { layoutFullName.setError("Enter Full Name"); return; }
            if (regNo.isEmpty()) { layoutRegNo.setError("Enter Registration Number"); return; }
            if (email.isEmpty()) { layoutEmail.setError("Enter Email"); return; }
            if (phone.isEmpty()) { layoutPhone.setError("Enter Phone Number"); return; }
            if (password.isEmpty() || password.length() < 6) { layoutPassword.setError("Password must be at least 6 characters"); return; }

            // Hapa unaweza kuweka code za kuhifadhi kwenye SQLite Database
            // boolean isInserted = dbHelper.registerUser(fullName, regNo, email, phone, password);
            // if (isInserted) { ... }

            Toast.makeText(RegisterActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();

            // Next Step: Open LoginActivity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}