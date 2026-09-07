package com.example.udom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Blocks extends AppCompatActivity {

    CardView cardBlock1, cardBlock2, cardBlock3, cardBlock4, cardBlock5, cardBlock6;
    ImageView btnBack; // Kitufe cha kurudi nyuma

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocks);

        // Kuunganisha CardViews na ID zake za kwenye XML
        cardBlock1 = findViewById(R.id.cardBlock1);
        cardBlock2 = findViewById(R.id.cardBlock2);
        cardBlock3 = findViewById(R.id.cardBlock3);
        cardBlock4 = findViewById(R.id.cardBlock4);
        cardBlock5 = findViewById(R.id.cardBlock5);
        cardBlock6 = findViewById(R.id.cardBlock6);
        btnBack = findViewById(R.id.btnBack); // Kuunganisha Back Button

        // Kazi ya Back Button (Kurudi nyuma ukibonyeza mshale)
        btnBack.setOnClickListener(v -> finish());

        // Kuweka Click Listeners kwa ajili ya Block 1 hadi 6
        cardBlock1.setOnClickListener(v -> openRoomActivity("Block 1"));
        cardBlock2.setOnClickListener(v -> openRoomActivity("Block 2"));
        cardBlock3.setOnClickListener(v -> openRoomActivity("Block 3"));
        cardBlock4.setOnClickListener(v -> openRoomActivity("Block 4 (CIVE)"));
        cardBlock5.setOnClickListener(v -> openRoomActivity("Block 5"));
        cardBlock6.setOnClickListener(v -> openRoomActivity("Block 6"));
    }

    // Njia ya kufungua ukurasa wa vyumba na kupitisha jina la block iliyochaguliwa
    private void openRoomActivity(String blockName) {
        Intent intent = new Intent(Blocks.this, Room.class);
        intent.putExtra("BLOCK_NAME", blockName);
        startActivity(intent);
    }
}