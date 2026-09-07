package com.example.udom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

public class Accomodation extends Fragment {

    private TextView tvStatusBadge, tvRoomName, tvBedInfo, tvEmptyMessage;
    private CardView cardAllocationDetails;
    private Button btnDownloadSlip;

    public Accomodation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accomodation, container, false);

        // Kuunganisha Views na XML IDs zake ndani ya Fragment
        tvStatusBadge = view.findViewById(R.id.tvStatusBadge);
        tvRoomName = view.findViewById(R.id.tvRoomName);
        tvBedInfo = view.findViewById(R.id.tvBedInfo);
        tvEmptyMessage = view.findViewById(R.id.tvEmptyMessage);
        cardAllocationDetails = view.findViewById(R.id.cardAllocationDetails);
        btnDownloadSlip = view.findViewById(R.id.btnDownloadSlip);

        if (btnDownloadSlip != null) {
            btnDownloadSlip.setOnClickListener(v ->
                    Toast.makeText(getContext(), "Downloading Allocation Slip...", Toast.LENGTH_SHORT).show()
            );
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAllocationData();
    }

    private void loadAllocationData() {
        if (getContext() == null) return;

        // Kusoma taarifa kutoka SharedPreferences ile ile ya "UserAllocation"
        SharedPreferences pref = getContext().getSharedPreferences("UserAllocation", Context.MODE_PRIVATE);
        String room = pref.getString("room", "").trim();
        String block = pref.getString("block", "").trim();
        String bed = pref.getString("bed", "").trim();

        // Angalia kama chumba kimeshachaguliwa
        if (!room.isEmpty() && !room.equalsIgnoreCase("null")) {
            if (tvEmptyMessage != null) tvEmptyMessage.setVisibility(View.GONE);
            if (cardAllocationDetails != null) cardAllocationDetails.setVisibility(View.VISIBLE);
            if (btnDownloadSlip != null) btnDownloadSlip.setVisibility(View.VISIBLE);

            if (tvStatusBadge != null) tvStatusBadge.setText("ALLOCATED & VERIFIED");
            if (tvRoomName != null) tvRoomName.setText(room + " - " + block);
            if (tvBedInfo != null) tvBedInfo.setText("Bed Position: " + bed);
        } else {
            if (tvEmptyMessage != null) {
                tvEmptyMessage.setVisibility(View.VISIBLE);
                tvEmptyMessage.setText("You have not booked any room yet.");
            }
            if (cardAllocationDetails != null) cardAllocationDetails.setVisibility(View.GONE);
            if (btnDownloadSlip != null) btnDownloadSlip.setVisibility(View.GONE);
        }
    }
}