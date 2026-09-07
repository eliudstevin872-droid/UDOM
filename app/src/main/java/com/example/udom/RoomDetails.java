package com.example.udom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RoomDetails extends AppCompatActivity {

    private TextView tvRoom, tvBlock;
    private RadioGroup rgPaymentMethods, rgBeds;
    private RadioButton rbBankPayment, rbMobileMoney;
    private LinearLayout layoutBankOptions, layoutMobileOptions;
    private Spinner spinnerBank, spinnerMobileNetwork;
    private EditText etPhoneNumber;
    private Button btnConfirmBooking;
    private ImageView btnBack; // Kitufe cha kurudi nyuma

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        // Kuunganisha Views zote
        tvRoom = findViewById(R.id.tvRoom);
        tvBlock = findViewById(R.id.tvBlock);
        rgPaymentMethods = findViewById(R.id.rgPaymentMethods);
        rgBeds = findViewById(R.id.rgBeds);
        rbBankPayment = findViewById(R.id.rbBankPayment);
        rbMobileMoney = findViewById(R.id.rbMobileMoney);
        layoutBankOptions = findViewById(R.id.layoutBankOptions);
        layoutMobileOptions = findViewById(R.id.layoutMobileOptions);
        spinnerBank = findViewById(R.id.spinnerBank);
        spinnerMobileNetwork = findViewById(R.id.spinnerMobileNetwork);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        btnBack = findViewById(R.id.btnBack); // Kuunganisha Back Button

        // Kazi ya Back Button (Kurudi nyuma ukibonyeza mshale)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        String roomNumber = getIntent().getStringExtra("ROOM_NUMBER");
        String blockName = getIntent().getStringExtra("BLOCK_NAME");

        if (roomNumber != null && !roomNumber.isEmpty() && tvRoom != null) {
            tvRoom.setText(roomNumber);
        }
        if (blockName != null && !blockName.isEmpty() && tvBlock != null) {
            tvBlock.setText(blockName);
        }

        String[] banks = {"CRDB Bank", "NMB Bank", "NBC Bank"};
        ArrayAdapter<String> bankAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, banks);
        spinnerBank.setAdapter(bankAdapter);

        String[] networks = {"M-Pesa", "HaloPesa", "Mixx By yas", "Airtel Money"};
        ArrayAdapter<String> networkAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, networks);
        spinnerMobileNetwork.setAdapter(networkAdapter);

        rgPaymentMethods.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbBankPayment) {
                layoutBankOptions.setVisibility(View.VISIBLE);
                layoutMobileOptions.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbMobileMoney) {
                layoutBankOptions.setVisibility(View.GONE);
                layoutMobileOptions.setVisibility(View.VISIBLE);
            }
        });

        btnConfirmBooking.setOnClickListener(v -> handlePaymentProcess());
    }

    private void handlePaymentProcess() {
        int selectedPaymentId = rgPaymentMethods.getCheckedRadioButtonId();

        if (selectedPaymentId == R.id.rbMobileMoney) {
            String phone = etPhoneNumber.getText().toString().trim();
            if (phone.isEmpty()) {
                etPhoneNumber.setError("Enter phone number");
                return;
            }
            showMobilePINDialog(phone, spinnerMobileNetwork.getSelectedItem().toString());
        } else if (selectedPaymentId == R.id.rbBankPayment) {
            saveAllocationData();
            String controlNoMsg = "Use Control No: 991234567890 to complete payment via " + spinnerBank.getSelectedItem().toString();
            showSuccessDialog("Bank Booking Initiated", controlNoMsg);
        } else {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMobilePINDialog(String phone, String provider) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(provider + " Push Payment");
        builder.setMessage("Enter PIN to confirm payment of TZS 183,000 for Room Booking:");

        final EditText inputPin = new EditText(this);
        inputPin.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        builder.setView(inputPin);

        builder.setPositiveButton("PAY NOW", (dialog, which) -> {
            String pin = inputPin.getText().toString();
            if (!pin.isEmpty()) {
                saveAllocationData();
                showSuccessDialog("Successful!", "Your room has been allocated successfully via " + provider + ".");
            } else {
                Toast.makeText(RoomDetails.this, "Payment Cancelled: PIN required", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showSuccessDialog(String titleText, String messageText) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_success, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView tvTitle = dialogView.findViewById(R.id.tvDialogTitle);
        TextView tvMessage = dialogView.findViewById(R.id.tvDialogMessage);

        if (tvTitle != null) tvTitle.setText(titleText);
        if (tvMessage != null) tvMessage.setText(messageText);

        Button btnSuccessOk = dialogView.findViewById(R.id.btnSuccessOk);
        btnSuccessOk.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void saveAllocationData() {
        String room = (tvRoom != null && !tvRoom.getText().toString().isEmpty()) ? tvRoom.getText().toString() : "Room 2A";
        String block = (tvBlock != null && !tvBlock.getText().toString().isEmpty()) ? tvBlock.getText().toString() : "Block 6";

        String bed = "Bed 1";
        if (rgBeds != null) {
            int selectedBedId = rgBeds.getCheckedRadioButtonId();
            if (selectedBedId != -1) {
                RadioButton selectedBed = findViewById(selectedBedId);
                if (selectedBed != null) {
                    bed = selectedBed.getText().toString();
                }
            }
        }

        SharedPreferences pref = getSharedPreferences("UserAllocation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("room", room);
        editor.putString("block", block);
        editor.putString("bed", bed);
        editor.apply();
    }
}