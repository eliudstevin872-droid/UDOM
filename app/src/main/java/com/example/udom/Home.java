package com.example.udom;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Home extends Fragment {

    private Button btnBookNow, btnMyAllocation, btnProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 1. Bind Views
        btnBookNow = view.findViewById(R.id.btnViewRooms);
        btnMyAllocation = view.findViewById(R.id.btnMyAllocation);
        btnProfile = view.findViewById(R.id.btnProfile);

        // 2. Book A Room Button -> Sasa inaelekea kwenye ukurasa wa kuchagua Block (BlockActivity)
        btnBookNow.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), Blocks.class);
            startActivity(intent);
        });

        // 3. My Allocation Button
        btnMyAllocation.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), Allocation.class);
            startActivity(intent);
        });

        // 4. Profile Button
        btnProfile.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Profile())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}