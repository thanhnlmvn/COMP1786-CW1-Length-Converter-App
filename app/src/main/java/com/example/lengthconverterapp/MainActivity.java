package com.example.lengthconverterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner fromUnitSpinner, toUnitSpinner;
    private TextView resultText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.input_value);
        fromUnitSpinner = findViewById(R.id.from_unit_spinner);
        toUnitSpinner = findViewById(R.id.to_unit_spinner);
        resultText = findViewById(R.id.result_text);
        convertButton = findViewById(R.id.convert_button);

        // Set up spinners with units
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.length_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertLength();
            }
        });
    }

    private void convertLength() {
        String inputString = inputValue.getText().toString();

        if (inputString.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double input = Double.parseDouble(inputString);
        String fromUnit = fromUnitSpinner.getSelectedItem().toString();
        String toUnit = toUnitSpinner.getSelectedItem().toString();

        double result = convert(input, fromUnit, toUnit);
        resultText.setText(String.format("Result: %.2f %s", result, toUnit));
    }

    private double convert(double value, String fromUnit, String toUnit) {
        double meterValue;

        // Convert input to meters first
        switch (fromUnit) {
            case "Metre":
                meterValue = value;
                break;
            case "Millimetre":
                meterValue = value / 1000;
                break;
            case "Mile":
                meterValue = value * 1609.34;
                break;
            case "Foot":
                meterValue = value * 0.3048;
                break;
            default:
                meterValue = value;
        }

        // Convert meters to target unit
        switch (toUnit) {
            case "Metre":
                return meterValue;
            case "Millimetre":
                return meterValue * 1000;
            case "Mile":
                return meterValue / 1609.34;
            case "Foot":
                return meterValue / 0.3048;
            default:
                return meterValue;
        }
    }
}
