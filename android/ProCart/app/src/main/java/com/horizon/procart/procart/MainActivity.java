package com.horizon.procart.procart;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnMove;
    private EditText username;
    private TextView weightTextView;
    private NFCReader nfcReader;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        btnMove = findViewById(R.id.ok);
        username = findViewById(R.id.username);
        weightTextView = findViewById(R.id.weightTextView); // TextView to display weight

        // Initialize NFC reader
        nfcReader = new NFCReader(this);

        // Set up NFC adapter
        NfcManager nfcManager = (NfcManager) getSystemService(NFC_SERVICE);
        nfcAdapter = nfcManager.getDefaultAdapter();

        if (nfcAdapter == null || !nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is not available or not enabled.", Toast.LENGTH_SHORT).show();
        }

        // Set up the Move Button click listener
        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the username (user ID) is entered
                if (username.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "Enter User ID...", Toast.LENGTH_SHORT).show();
                } else {
                    // Get the entered user ID
                    String input = username.getText().toString();
                    Intent intent = new Intent(MainActivity.this, Bill.class);
                    intent.putExtra("input", input); // Pass the user ID to the next activity
                    startActivity(intent);
                }
            }
        });
    }

    // Handling NFC scan result
    @Override
    protected void onResume() {
        super.onResume();

        if (nfcAdapter != null) {
            // Enable NFC foreground dispatch
            nfcAdapter.enableForegroundDispatch(this, null, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (nfcAdapter != null) {
            // Disable NFC foreground dispatch when the activity is paused
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Check if the intent contains NFC tag data
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            // Read the weight data from the NFC tag
            nfcReader.readWeightData(tag);

            // Display the weight data
            String weight = nfcReader.getWeightData();
            if (weight != null && !weight.isEmpty()) {
                weightTextView.setText("Weight: " + weight + " kg");
            } else {
                weightTextView.setText("Failed to read weight.");
            }
        }
    }
}
