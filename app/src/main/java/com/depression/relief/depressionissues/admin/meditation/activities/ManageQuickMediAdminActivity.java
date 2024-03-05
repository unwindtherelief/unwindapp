package com.depression.relief.depressionissues.admin.meditation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.meditation.MeditationCategoryChooseActivity;
import com.depression.relief.depressionissues.admin.meditation.MeditationPagerAdapter;
import com.depression.relief.depressionissues.admin.meditation.QuickMeditationPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManageQuickMediAdminActivity extends AppCompatActivity {
    private ViewPager viewPager_tab;
    private TabLayout tabLayout;
    private FirebaseFirestore firestore;
    private CollectionReference meditationCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_quick_medi_admin);
        viewPager_tab = findViewById(R.id.viewPager_tab);
        tabLayout = findViewById(R.id.tabLayout);

        firestore = FirebaseFirestore.getInstance();
        meditationCollection = firestore.collection("quickmeditation");

        QuickMeditationPagerAdapter pagerAdapter = new QuickMeditationPagerAdapter(getSupportFragmentManager(), 8);
        viewPager_tab.setAdapter(pagerAdapter);

        // Set up TabLayout with ViewPager
        tabLayout.setupWithViewPager(viewPager_tab);

        // Set up FloatingActionButton click listener
        findViewById(R.id.fabAddMeditation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddMeditationActivity();
            }
        });
    }

    private void openAddMeditationActivity() {
        Intent intent = new Intent(ManageQuickMediAdminActivity.this, QuickMeditationDataAddActivity.class);
        startActivity(intent);
    }
}