package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.depression.relief.depressionissues.R;
import com.google.android.material.snackbar.Snackbar;

public class ChantCounterActivity extends AppCompatActivity {
    ImageView img_shlok1, img_shlok2, img_shlok3, img_shlok4, img_shlok5;
    ImageView btn_startChant;
    Spinner spinnerBeadNumber;
    private int selectedBeadNumber;
    private int selectedImageId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chant_counter);

        img_shlok1 = findViewById(R.id.img_shlok1);
        img_shlok2 = findViewById(R.id.img_shlok2);
        img_shlok3 = findViewById(R.id.img_shlok3);
        img_shlok4 = findViewById(R.id.img_shlok4);
        img_shlok5 = findViewById(R.id.img_shlok5);
        btn_startChant = findViewById(R.id.btn_startChant);
        spinnerBeadNumber = findViewById(R.id.spinnerBeadNumber);

        // Set up spinner with bead numbers
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bead_numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBeadNumber.setAdapter(adapter);

        spinnerBeadNumber.setSelection(adapter.getPosition("11"));

        spinnerBeadNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBeadNumber = Integer.parseInt(adapterView.getItemAtPosition(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        img_shlok1.setOnClickListener(v -> handleImageClick(v, R.id.img_shlok1));
        img_shlok2.setOnClickListener(v -> handleImageClick(v, R.id.img_shlok2));
        img_shlok3.setOnClickListener(v -> handleImageClick(v, R.id.img_shlok3));
        img_shlok4.setOnClickListener(v -> handleImageClick(v, R.id.img_shlok4));
        img_shlok5.setOnClickListener(v -> handleImageClick(v, R.id.img_shlok5));

        btn_startChant.setOnClickListener(v -> handleStartChantClick(v));


    }

    private void handleStartChantClick(View view) {
        if (selectedImageId == 0) {
            Snackbar.make(view, "Please select an image.", Snackbar.LENGTH_SHORT).show();
        } else if (selectedBeadNumber == 0) {
            Snackbar.make(view, "Please select a bead count.", Snackbar.LENGTH_SHORT).show();
        } else {
            // Start ChantShlokActivity
            Intent intent = new Intent(ChantCounterActivity.this, ChantShlokActivity.class);
            intent.putExtra("categoryPosition", getCategoryPosition(selectedImageId));
            intent.putExtra("beadCount", selectedBeadNumber);
            startActivity(intent);
        }
    }


    private void handleImageClick(View clickedView, int imageId) {
        // Update the selected image ID
        selectedImageId = imageId;

        // Highlight the selected image (you can add your logic here)
        // For example, you can change the background color or add a border to the selected image

        // Show a message or perform any other actions on image click
        Snackbar.make(clickedView, "Image selected: " + getResources().getResourceEntryName(imageId), Snackbar.LENGTH_SHORT).show();
    }

    private int getClickedImageId(View clickedView) {
        if (clickedView.getId() == R.id.img_shlok1) {
            return R.id.img_shlok1;
        } else if (clickedView.getId() == R.id.img_shlok2) {
            return R.id.img_shlok2;
        } else if (clickedView.getId() == R.id.img_shlok3) {
            return R.id.img_shlok3;
        } else if (clickedView.getId() == R.id.img_shlok4) {
            return R.id.img_shlok4;
        } else if (clickedView.getId() == R.id.img_shlok5) {
            return R.id.img_shlok5;
        }
        return 0;
    }


    private void startChantShlokActivity(int clickedImageId) {
        // Extract the category position from the clicked image
        int categoryPosition = getCategoryPosition(clickedImageId);

        // Get the selected bead number from the spinner
        String selectedBeadNumber = spinnerBeadNumber.getSelectedItem().toString();

        // Start ChantShlokActivity
        Intent intent = new Intent(ChantCounterActivity.this, ChantShlokActivity.class);
        intent.putExtra("categoryPosition", categoryPosition);
        intent.putExtra("beadCount", selectedBeadNumber);
        startActivity(intent);
    }

    private int getCategoryPosition(int clickedImageId) {
        switch (clickedImageId) {
            case R.id.img_shlok1:
                return 1;
            case R.id.img_shlok2:
                return 2;
            case R.id.img_shlok3:
                return 3;
            case R.id.img_shlok4:
                return 4;
            case R.id.img_shlok5:
                return 5;
            default:
                return 1;
        }
    }
}