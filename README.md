# Smart Checkout

## Note:
- **Original GitHub project**: [Walmart Smart Shopping Cart](https://github.com/HeliosX7/Walmart-Smart-Shopping-Cart/tree/master)
- Since our project was built using PowerApps, we used the code from the GitHub project as a base and modified it by integrating the generated code to fit our requirements.
- **Modifications**:
  - Replaced QR code functionality with NFC to read weight data.
  - Added an AI model for detecting vegetables (tomato, cucumber, and potato).

## Classes Overview:

### 1. **MainActivity**
The main activity of the application. It handles the user interface and integrates various components:
- It listens for NFC tag scans and uses the NFC data to get the weight of the item.
- It integrates AI vegetable detection using the camera.
- Based on the detected vegetable and weight, it calculates the total price for each item and displays it in the cart.

### 2. **NFCReader**
This class manages NFC functionality. It is responsible for:
- Starting and stopping NFC listening.
- Reading NFC tags to fetch weight information for each item scanned.

### 3. **VegetableDetection**
This class handles AI vegetable detection:
- It processes the camera frames to detect specific vegetables (tomato, cucumber, and potato).
- Once a vegetable is detected, it returns the name of the vegetable (e.g., tomato, cucumber, or potato).

### 4. **PriceCalculator**
This class calculates the price for a vegetable item based on its name and weight:
- For each detected vegetable, it uses predefined prices per kilogram to calculate the price based on the weight read from the NFC tag.

### 5. **Item**
This class represents an item in the shopping cart:
- It stores the item name, quantity (weight), and unit price.
- It provides a method to calculate the total price for an item by multiplying the price by the quantity (weight).

### 6. **ItemsList**
This class is a custom `ArrayAdapter` used to display the list of items in the cart:
- It binds the data from the `Item` objects to the UI, displaying the item name, quantity (weight), price, and total price in the cart.






