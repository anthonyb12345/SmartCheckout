package com.horizon.procart.procart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.opencv.core.Mat;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private VegetableDetection vegetableDetection;
    private NFCReader nfcReader;
    private PriceCalculator priceCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the vegetable detection, NFC, and price calculator
        vegetableDetection = new VegetableDetection(this);
        nfcReader = new NFCReader(this);
        priceCalculator = new PriceCalculator();

        // Check if OpenCV is loaded correctly
        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(this, "OpenCV initialization failed", Toast.LENGTH_SHORT).show();
        }

        // Start NFC listening (this should be handled in the onResume for example)
        nfcReader.startListening();
    }

    // Method to process camera frame and integrate NFC reading and price calculation
    private void processCameraFrame(Mat frame) {
        // Call VegetableDetection to detect the vegetable in the current frame
        String detectedVegetable = vegetableDetection.detectVegetable(frame);

        // Simulate NFC reading for weight
        float weight = nfcReader.readWeightFromNFC(null);  // You should pass the actual NFC tag here

        // Get the price of the detected vegetable
        if (detectedVegetable != null) {
            float price = priceCalculator.getPrice(detectedVegetable, weight);
            Toast.makeText(this, detectedVegetable + " detected. Weight: " + weight + "kg. Price: $" + price, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No vegetable detected", Toast.LENGTH_SHORT).show();
        }
    }
}

