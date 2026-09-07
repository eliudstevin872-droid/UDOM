package com.example.udom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Room extends AppCompatActivity {

    private TextView tvSelectedBlockTitle, tvRoom1Title, tvRoom2Title, tvRoom3Title;
    private Button btnRoom1, btnRoom2, btnRoom3;
    private ImageView btnBack; // Kitufe cha kurudi nyuma

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // 0. Kuunganisha na kuweka kazi kwenye Back Button
        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 1. Pokea jina la Block lililotumwa kutoka BlockActivity
        String selectedBlock = getIntent().getStringExtra("BLOCK_NAME");
        if (selectedBlock == null) {
            selectedBlock = "Block 1";
        }

        final String currentBlock = selectedBlock;

        // 2. Onyesha jina la block kwenye kichwa cha juu
        tvSelectedBlockTitle = findViewById(R.id.tvSelectedBlockTitle);
        if (tvSelectedBlockTitle != null) {
            tvSelectedBlockTitle.setText("Room for: " + currentBlock);
        }

        // 3. Unganisha Vichwa vya Vyumba (TextViews)
        tvRoom1Title = findViewById(R.id.tvRoom1Title);
        tvRoom2Title = findViewById(R.id.tvRoom2Title);
        tvRoom3Title = findViewById(R.id.tvRoom3Title);

        // 4. Unganisha Vifungo (Buttons)
        btnRoom1 = findViewById(R.id.btnRoom101);
        btnRoom2 = findViewById(R.id.btnRoom103);
        btnRoom3 = findViewById(R.id.btnRoom205);

        // 5. Badilisha majina kwenye Kichwa cha kadi na uweke "VIEW ROOM" kwenye Button
        if (currentBlock.contains("Block 1")) {
            if (tvRoom1Title != null) tvRoom1Title.setText("Room 1A - S12");
            if (btnRoom1 != null) {
                btnRoom1.setText("VIEW ROOM");
                btnRoom1.setOnClickListener(v -> openRoomDetails("Room 1A - S12", currentBlock));
            }

            if (tvRoom2Title != null) tvRoom2Title.setText("Room 1A - S14");
            if (btnRoom2 != null) {
                btnRoom2.setText("VIEW ROOM");
                btnRoom2.setOnClickListener(v -> openRoomDetails("Room 1A - S14", currentBlock));
            }

            if (tvRoom3Title != null) tvRoom3Title.setText("Room 1B - G02");
            if (btnRoom3 != null) {
                btnRoom3.setText("VIEW ROOM");
                btnRoom3.setOnClickListener(v -> openRoomDetails("Room 1B - G02", currentBlock));
            }

        } else if (currentBlock.contains("Block 4")) {
            if (tvRoom1Title != null) tvRoom1Title.setText("Room 4A - F15");
            if (btnRoom1 != null) {
                btnRoom1.setText("VIEW ROOM");
                btnRoom1.setOnClickListener(v -> openRoomDetails("Room 4A - F15", currentBlock));
            }

            if (tvRoom2Title != null) tvRoom2Title.setText("Room 4A - F16");
            if (btnRoom2 != null) {
                btnRoom2.setText("VIEW ROOM");
                btnRoom2.setOnClickListener(v -> openRoomDetails("Room 4A - F16", currentBlock));
            }

            if (tvRoom3Title != null) tvRoom3Title.setText("Room 4B - T01");
            if (btnRoom3 != null) {
                btnRoom3.setText("VIEW ROOM");
                btnRoom3.setOnClickListener(v -> openRoomDetails("Room 4B - T01", currentBlock));
            }

        } else {
            if (tvRoom1Title != null) tvRoom1Title.setText("Room 2A - G01");
            if (btnRoom1 != null) {
                btnRoom1.setText("VIEW ROOM");
                btnRoom1.setOnClickListener(v -> openRoomDetails("Room 2A - G01", currentBlock));
            }

            if (tvRoom2Title != null) tvRoom2Title.setText("Room 2B - F05");
            if (btnRoom2 != null) {
                btnRoom2.setText("VIEW ROOM");
                btnRoom2.setOnClickListener(v -> openRoomDetails("Room 2B - F05", currentBlock));
            }

            if (tvRoom3Title != null) tvRoom3Title.setText("Room 3B - S10");
            if (btnRoom3 != null) {
                btnRoom3.setText("VIEW ROOM");
                btnRoom3.setOnClickListener(v -> openRoomDetails("Room 3B - S10", currentBlock));
            }
        }
    }

    private void openRoomDetails(String roomNumber, String blockName) {
        Intent intent = new Intent(Room.this, RoomDetails.class);
        intent.putExtra("ROOM_NUMBER", roomNumber);
        intent.putExtra("BLOCK_NAME", blockName);
        startActivity(intent);
    }
}