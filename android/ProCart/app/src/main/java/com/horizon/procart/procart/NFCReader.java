package com.horizon.procart.procart;

import android.content.Context;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefMessage;
import android.nfc.tech.NdefRecord;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class NFCReader {

    private Context context;
    private String weightData = "";  // Store weight data as a string

    public NFCReader(Context context) {
        this.context = context;
    }

    // Method to process the NFC tag and read the weight data
    public void readWeightData(Tag tag) {
        Ndef ndef = Ndef.get(tag);

        if (ndef != null) {
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();
            if (ndefMessage != null) {
                for (NdefRecord ndefRecord : ndefMessage.getRecords()) {
                    if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                        NdefRecord.RTD_TEXT.equals(ndefRecord.getType())) {

                        try {
                            weightData = readText(ndefRecord);
                            Toast.makeText(context, "Weight read: " + weightData + " kg", Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            Log.e("NFCReader", "Unsupported Encoding", e);
                        }
                    }
                }
            }
        } else {
            Toast.makeText(context, "NFC Tag is empty or incompatible", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to extract text from an NDEF record
    private String readText(NdefRecord record) throws UnsupportedEncodingException {
        byte[] payload = record.getPayload();

        // Determine text encoding
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;

        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

    // Getter to access the read weight data from other parts of the app
    public String getWeightData() {
        return weightData;
    }
}

