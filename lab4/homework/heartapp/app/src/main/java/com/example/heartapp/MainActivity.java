package com.example.heartapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView heartRateTextView;
    private EditText heightEditText;
    private EditText weightEditText;
    private Button measureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        heartRateTextView = findViewById(R.id.heartRateTextView);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        measureButton = findViewById(R.id.measureButton);

        // Set onClickListener for the measureButton
        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulate measuring heart rate
                int heartRate = generateRandomHeartRate();
                // Update the TextView with the measured heart rate
                heartRateTextView.setText("Heart Rate: " + heartRate);

                // Get user input for height and weight
                String heightStr = heightEditText.getText().toString();
                String weightStr = weightEditText.getText().toString();

                // Convert height and weight to appropriate data types
                if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
                    double height = Double.parseDouble(heightStr);
                    double weight = Double.parseDouble(weightStr);

                    // Here you can use the height and weight data as needed
                    // For example, you can calculate BMI, etc.
                }
            }
        });
    }

    // Method to generate a random heart rate between 60 and 100
    private int generateRandomHeartRate() {
        Random random = new Random();
        return random.nextInt(41) + 60; // Generates a random number between 60 and 100
    }
}
