package com.horizon.procart.procart;

public class PriceCalculator {

    public float getPrice(String vegetable, float weight) {
        float pricePerKg = 0.0f;

        // Define price per kilogram for each vegetable
        switch (vegetable) {
            case "tomato":
                pricePerKg = 3.0f;  // Example price per kg for tomato
                break;
            case "cucumber":
                pricePerKg = 2.5f;  // Example price per kg for cucumber
                break;
            case "potato":
                pricePerKg = 1.8f;  // Example price per kg for potato
                break;
            default:
                break;
        }

        // Calculate the total price based on weight
        return pricePerKg * weight;
    }
}
