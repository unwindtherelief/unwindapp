package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.depression.relief.depressionissues.R;
import com.shawnlin.numberpicker.NumberPicker;

public class BrethingExerciseActvity extends AppCompatActivity {
    NumberPicker numberPicker;
    LinearLayout lnr_breath_relax, lnr_breath_deep, lnr_breath_belly, lnr_breath_counting, lnr_breath_resonant;
    int selectedTime = 5;
    int selectedCategory = -1;
    ImageView btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brething_exercise_actvity);


        numberPicker = findViewById(R.id.countNumberPicker);
        lnr_breath_relax = findViewById(R.id.lnr_breath_relax);
        lnr_breath_deep = findViewById(R.id.lnr_breath_deep);
        lnr_breath_belly = findViewById(R.id.lnr_breath_belly);
        lnr_breath_counting = findViewById(R.id.lnr_breath_counting);
        lnr_breath_resonant = findViewById(R.id.lnr_breath_resonant);
        btnStart = findViewById(R.id.btnStart);

        numberPicker.setValue(selectedTime);

        lnr_breath_relax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryClick(1);
            }
        });
        lnr_breath_deep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryClick(2);
            }
        });
        lnr_breath_belly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryClick(3);
            }
        });
        lnr_breath_counting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryClick(4);
            }
        });
        lnr_breath_resonant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryClick(5);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAnyCategorySelected()) {
                    showSnackbar("Please select at least one Exercise");
                    return;
                }

                // Start BreathMotionShowActivity with selected category and time
                startBreathMotionActivity();
            }
        });
    }

    private void startBreathMotionActivity() {
        Intent intent = new Intent(this, BreathMotionShowActivity.class);
        intent.putExtra("animationCount", numberPicker.getValue());
        intent.putExtra("selectedCategory", selectedCategory);
        startActivity(intent);
    }

    private void showSnackbar(String message) {
            // Implement Snackbar logic
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

    private boolean isAnyCategorySelected() {
        return selectedCategory != -1;
    }

    private void onCategoryClick(int categoryIndex) {
        resetCategoryBackgrounds();
        selectedCategory = categoryIndex;
        switch (categoryIndex) {
            case 1:
                lnr_breath_relax.setBackgroundResource(R.drawable.rounbg_yellowripple);
                break;
            case 2:
                lnr_breath_deep.setBackgroundResource(R.drawable.rounbg_yellowripple);
                break;
            case 3:
                lnr_breath_belly.setBackgroundResource(R.drawable.rounbg_yellowripple);
                break;
            case 4:
                lnr_breath_counting.setBackgroundResource(R.drawable.rounbg_yellowripple);
                break;
            case 5:
                lnr_breath_resonant.setBackgroundResource(R.drawable.rounbg_yellowripple);
                break;
        }
    }

    private void resetCategoryBackgrounds() {
        lnr_breath_relax.setBackgroundResource(R.drawable.round_gray);
        lnr_breath_deep.setBackgroundResource(R.drawable.round_gray);
        lnr_breath_belly.setBackgroundResource(R.drawable.round_gray);
        lnr_breath_counting.setBackgroundResource(R.drawable.round_gray);
        lnr_breath_resonant.setBackgroundResource(R.drawable.round_gray);

    }
}