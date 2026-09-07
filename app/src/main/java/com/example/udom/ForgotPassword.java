package com.example.udom;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class ForgotPassword extends AppCompatActivity {

    private ImageView btnBack;
    private LinearLayout layoutStep1, layoutStep2;
    private TextInputEditText editStudentID, editPhone, editOtp, editNewPassword, editConfirmPassword;
    private TextInputLayout layoutStudentID, layoutPhone, layoutOtp, layoutNewPassword, layoutConfirmPassword;
    private Button btnSendCode, btnResetPassword;

    private String generatedCode = "";
    private static final String CHANNEL_ID = "OTP_NOTIFICATION_CHANNEL";
    private static final int PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.ime() | WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        createNotificationChannel();

        // View Bindings
        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        layoutStep1 = findViewById(R.id.layoutStep1);
        layoutStep2 = findViewById(R.id.layoutStep2);

        layoutStudentID = findViewById(R.id.layoutStudentId);
        layoutPhone = findViewById(R.id.layoutPhone);
        layoutOtp = findViewById(R.id.layoutOtp);
        layoutNewPassword = findViewById(R.id.layoutNewPassword);
        layoutConfirmPassword = findViewById(R.id.layoutConfirmPassword); // Mpya

        editStudentID = findViewById(R.id.editStudentId);
        editPhone = findViewById(R.id.editPhone);
        editOtp = findViewById(R.id.editOtp);
        editNewPassword = findViewById(R.id.editNewPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword); // Mpya

        btnSendCode = findViewById(R.id.btnSendCode);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        // Hatua ya 1: Kutuma Code
        btnSendCode.setOnClickListener(v -> {
            String studentId = editStudentID.getText() != null ? editStudentID.getText().toString().trim() : "";
            String phone = editPhone.getText() != null ? editPhone.getText().toString().trim() : "";

            layoutStudentID.setError(null);
            layoutPhone.setError(null);

            if (studentId.isEmpty()) {
                layoutStudentID.setError("Please enter Student ID");
                return;
            }

            if (phone.isEmpty() || phone.length() < 10) {
                layoutPhone.setError("Enter a valid registered phone number");
                return;
            }

            // Kagua ruhusa ya Notification kwa Android 13+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
                    return;
                }
            }

            sendOtpProcess();
        });

        // Hatua ya 2: Kuhakiki Code na Kubadilisha Password
        btnResetPassword.setOnClickListener(v -> {
            String inputCode = editOtp.getText() != null ? editOtp.getText().toString().trim() : "";
            String newPassword = editNewPassword.getText() != null ? editNewPassword.getText().toString().trim() : "";
            String confirmPassword = editConfirmPassword.getText() != null ? editConfirmPassword.getText().toString().trim() : "";

            layoutOtp.setError(null);
            layoutNewPassword.setError(null);
            layoutConfirmPassword.setError(null);

            if (inputCode.isEmpty() || !inputCode.equals(generatedCode)) {
                layoutOtp.setError("Invalid Verification Code!");
                return;
            }

            if (newPassword.isEmpty() || newPassword.length() < 6) {
                layoutNewPassword.setError("Password must be at least 6 characters");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                layoutConfirmPassword.setError("Passwords do not match");
                return;
            }

            Toast.makeText(this, "Password reset successful! Please Login.", Toast.LENGTH_LONG).show();
            finish();
        });
    }

    private void sendOtpProcess() {
        Random random = new Random();
        generatedCode = String.format("%04d", random.nextInt(10000));

        showOtpNotification(generatedCode);

        layoutStep1.setVisibility(View.GONE);
        layoutStep2.setVisibility(View.VISIBLE);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "OTP Verification";
            String description = "Channel for sending OTP codes";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void showOtpNotification(String code) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.udom_logo)
                .setContentTitle("UDOMStay Verification Code")
                .setContentText("Your OTP code is: " + code)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1001, builder.build());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendOtpProcess();
            } else {
                Toast.makeText(this, "Permission denied! Cannot show OTP notification.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}