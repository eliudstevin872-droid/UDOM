package com.example.udom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Allocation extends AppCompatActivity {

    private TextView tvStatusBadge, tvRoomName, tvBedInfo, tvEmptyMessage;
    private CardView cardAllocationDetails;
    private Button btnDownloadSlip;
    private ImageView btnBack; // Iliyoongezwa kwa ajili ya kurudi nyuma

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation);

        // Kuunganisha Views na XML IDs zake
        tvStatusBadge = findViewById(R.id.tvStatusBadge);
        tvRoomName = findViewById(R.id.tvRoomName);
        tvBedInfo = findViewById(R.id.tvBedInfo);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);
        cardAllocationDetails = findViewById(R.id.cardAllocationDetails);
        btnDownloadSlip = findViewById(R.id.btnDownloadSlip);
        btnBack = findViewById(R.id.btnBack); // Kuunganisha Back Button

        // Kazi ya Back Button (Kurudi nyuma ukibonyeza mshale)
        btnBack.setOnClickListener(v -> finish());

        btnDownloadSlip.setOnClickListener(v ->
                Toast.makeText(Allocation.this, "Downloading Allocation Slip...", Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllocationData();
    }

    private void loadAllocationData() {
        // Kusoma taarifa kutoka SharedPreferences
        SharedPreferences pref = getSharedPreferences("UserAllocation", Context.MODE_PRIVATE);
        String room = pref.getString("room", "").trim();
        String block = pref.getString("block", "").trim();
        String bed = pref.getString("bed", "").trim();

        // Angalia kama kweli chumba kimechaguliwa na kuhifadhiwa
        if (!room.isEmpty() && !room.equalsIgnoreCase("null")) {
            // Hali ya KWANZA: Umechagua na ku-confirm chumba
            tvEmptyMessage.setVisibility(View.GONE);
            cardAllocationDetails.setVisibility(View.VISIBLE);
            btnDownloadSlip.setVisibility(View.VISIBLE);

            tvStatusBadge.setText("ALLOCATED & VERIFIED");
            tvRoomName.setText(room + " - " + block);
            tvBedInfo.setText("Bed Position: " + bed);
        } else {
            // Hali ya PILI: Bado hujachagua chumba kabisa
            tvEmptyMessage.setVisibility(View.VISIBLE);
            tvEmptyMessage.setText("You have not booked any room yet.");

            cardAllocationDetails.setVisibility(View.GONE);
            btnDownloadSlip.setVisibility(View.GONE);
        }
    }
}