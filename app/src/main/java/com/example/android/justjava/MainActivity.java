package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.createChooser;
import static com.example.android.justjava.R.string.person_name;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    boolean whippedCream, chocolate;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);
        if (Integer.parseInt(((TextView) findViewById(R.id.quantity_text_view)).getText().toString()) >= 100) {
            ((Button) findViewById(R.id.plus_button)).setEnabled(false);
        }
    }

    public void decrement(View view) {
        quantity--;
        displayQuantity(quantity);
        if (Integer.parseInt(((TextView) findViewById(R.id.quantity_text_view)).getText().toString()) <= 1) {
            ((Button) findViewById(R.id.minus_button)).setEnabled(false);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        whippedCream = ((CheckBox) findViewById(R.id.notify_me_checkbox)).isChecked();
        chocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();
        int singlePrice = ((whippedCream) ? 1 : 0) + ((chocolate) ? 2 : 0) + 5;
        name = ((EditText) findViewById(R.id.name)).getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, displaySummary(quantity, singlePrice));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private String displaySummary(int number, int singlePrice) {
        return getString(R.string.email_content, name, singlePrice * number);
    }
}