package com.horizon.procart.procart;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.opencv.core.Mat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private VegetableDetection vegetableDetection;
    private NFCReader nfcReader;
    private PriceCalculator priceCalculator;
    private ArrayList<Item> cartItems;  // To store the items added to the cart
    private ItemsList itemsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the vegetable detection, NFC, and price calculator
        vegetableDetection = new VegetableDetection(this);
        nfcReader = new NFCReader(this);
        priceCalculator = new PriceCalculator();
        cartItems = new ArrayList<>();

        // Initialize the adapter for displaying items (assuming you have a ListView in your layout)
        itemsListAdapter = new ItemsList(this, cartItems);

        // Start NFC listening (this should be handled in onResume for example)
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

            // Create an item with the detected vegetable, its quantity (weight), and price
            Item newItem = new Item(detectedVegetable, weight, (int) price);
            cartItems.add(newItem);  // Add item to the cart

            // Update the ListView to show the new item
            itemsListAdapter.notifyDataSetChanged();

            // Calculate the total amount for the cart
            float totalAmount = calculateTotalAmount();
            Toast.makeText(this, detectedVegetable + " added. Total: $" + totalAmount, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No vegetable detected", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to calculate the total price of all items in the cart
    private float calculateTotalAmount() {
        float totalAmount = 0.0f;
        for (Item item : cartItems) {
            totalAmount += item.getTotalPrice();  // Add the total price of each item
        }
        return totalAmount;
    }
}


