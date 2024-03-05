package com.depression.relief.depressionissues.admin.meditation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.meditation.activities.ManageQuickMediAdminActivity;
import com.depression.relief.depressionissues.admin.meditation.activities.MeditationManageAdminActivity;

public class MeditationCategoryChooseActivity extends AppCompatActivity {

    ImageView btnGuidedMeditation, btnQuickMeditation, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_category_choose);

        btnGuidedMeditation = findViewById(R.id.btnGuidedMeditation);
        btnQuickMeditation = findViewById(R.id.btnQuickMeditation);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnGuidedMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeditationCategoryChooseActivity.this, MeditationManageAdminActivity.class));
            }
        });

        btnQuickMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeditationCategoryChooseActivity.this, ManageQuickMediAdminActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}