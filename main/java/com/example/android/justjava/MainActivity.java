/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file. **/


package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
            * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot order more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot order less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    /**
     This method is called when the order button is clicked
     */
    public void submitOrder(View view) {
       EditText nameField = (EditText) findViewById(R.id.name_field);
       String name = nameField.getText().toString();

       CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
       boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

       CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
       boolean hasChocolate = chocolateCheckbox.isChecked();

       int price = calculatePrice(hasWhippedCream, hasChocolate);
       String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        String address = "funcrushed@hotmail.com";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:" + address));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


//       displayMessage(priceMessage);


    }



    /**
     * Calculates the price of the order
     *
     * @param haswhippedCream if the user wants whipped cream
     * @param  hasChocolate if the user wants chocolate
     * @return price
     */
    private int calculatePrice(boolean haswhippedCream, boolean hasChocolate) {
        //Price of 1 cup of coffee
        int basePrice = 5;

        //Add $1 for Whipped cream
        if (haswhippedCream) {
            basePrice = basePrice +1;
        }

        //add $2 if the user wants chocolate
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     * Create summary of the order
     *
     * @param name of the customer
     * @param price of the order
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is wether or not the user wants chocolate
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {

        String priceMessage = getString(R.string.name);
        priceMessage += "\nAdd " + getString(R.string.whippedCream) + " ? " + hasWhippedCream;
        priceMessage += "\nAdd Chocolate? " + hasChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank You!";
        return priceMessage;

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }


}
