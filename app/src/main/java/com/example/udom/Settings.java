package com.example.udom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class Settings extends AppCompatActivity {

    // Zimebadilishwa kuwa LinearLayout ili zilingane na ID zilizopo kwenye XML
    private LinearLayout layoutResetBooking, layoutContactSupport, layoutAboutApp;
    private SwitchCompat switchDarkMode;
    private ImageView btnBack; // Kitufe cha kurudi nyuma
    private SharedPreferences appSettingsPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        appSettingsPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE);

        layoutResetBooking = findViewById(R.id.layoutResetBooking);
        layoutContactSupport = findViewById(R.id.layoutContactSupport);
        layoutAboutApp = findViewById(R.id.layoutAboutApp);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        btnBack = findViewById(R.id.btnBack); // Kuunganisha Back Button

        // 0. Kazi ya Back Button (Kurudi nyuma ukibonyeza mshale)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 1. Angalia chaguo la mtumiaji lililohifadhiwa awali
        boolean isDarkMode = appSettingsPref.getBoolean("DarkMode", false);
        switchDarkMode.setChecked(isDarkMode);

        // 2. Badilisha Theme kulingana na uamuzi wa mtumiaji pekee
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = appSettingsPref.edit();
            editor.putBoolean("DarkMode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // 3. Reset Booking Action
        layoutResetBooking.setOnClickListener(v -> showResetConfirmationDialog());

        // 4. Contact Support Action
        layoutContactSupport.setOnClickListener(v -> {
            Toast.makeText(Settings.this, "Opening support details...", Toast.LENGTH_SHORT).show();
        });

        // 5. About App Dialog
        layoutAboutApp.setOnClickListener(v -> {
            new AlertDialog.Builder(Settings.this)
                    .setTitle("About UDOMStay")
                    .setMessage("UDOMStay is an official hostel allocation and room management mobile application developed for UDOM CIVE students.\n\nVersion: 1.0\nYear: 2026")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void showResetConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Reset Booking")
                .setMessage("Are you sure you want to clear your current room allocation? This action cannot be undone.")
                .setPositiveButton("YES, RESET", (dialog, which) -> {
                    SharedPreferences pref = getSharedPreferences("UserAllocation", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.apply();

                    Toast.makeText(Settings.this, "Booking data cleared successfully!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .show();
    }
}