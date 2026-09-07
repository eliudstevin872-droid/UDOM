package com.example.udom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Fragment {

    private TextView tvProfileName, tvProfileRole, tvRegNumber, tvEmail, tvPhone;
    private Button btnEditProfile;
    private ImageView btnBack;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Kuunganisha Views na XML IDs zake halisi
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileRole = view.findViewById(R.id.tvProfileRole);
        tvRegNumber = view.findViewById(R.id.tvRegNumber);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnBack = view.findViewById(R.id.btnBack);

        // Kupakia taarifa za mtumiaji
        loadUserProfileData();

        // Kazi ya Back Button (Kama ipo ndani ya Activity/Fragment navigation)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            });
        }

        // Kazi ya Edit Profile Button
        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v ->
                    Toast.makeText(getContext(), "Edit Profile feature coming soon!", Toast.LENGTH_SHORT).show()
            );
        }

        return view;
    }

    private void loadUserProfileData() {
        if (getContext() == null) return;

        // Kusoma taarifa kutoka SharedPreferences (Unaweza kubadilisha iwe 'UserSession' au 'UserAllocation')
        SharedPreferences pref = getContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        // Kutumia taarifa za msingi au zile zilizohifadhiwa
        String fullName = pref.getString("full_name", "~∆e.NSlayer 👽");
        String role = pref.getString("role", "Student - UDOM CIVE");
        String regNo = pref.getString("reg_no", "T25-03-12345");
        String email = pref.getString("email", "phoenix@udom.ac.tz");
        String phone = pref.getString("phone", "+255 713 303 485");

        if (tvProfileName != null) tvProfileName.setText(fullName);
        if (tvProfileRole != null) tvProfileRole.setText(role);
        if (tvRegNumber != null) tvRegNumber.setText(regNo);
        if (tvEmail != null) tvEmail.setText(email);
        if (tvPhone != null) tvPhone.setText(phone);
    }
}