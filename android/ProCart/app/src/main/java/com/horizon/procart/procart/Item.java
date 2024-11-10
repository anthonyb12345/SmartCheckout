package com.horizon.procart.procart;

public class Item {
    String item;
    float quantity;  // Changed to float to handle fractional quantities
    int price;

    public Item() {}

    public Item(String item, float quantity, int price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Method to calculate total price for this item
    public float getTotalPrice() {
        return price * quantity;
    }
}
