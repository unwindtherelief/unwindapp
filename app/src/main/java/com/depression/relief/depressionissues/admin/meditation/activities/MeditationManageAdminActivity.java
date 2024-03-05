package com.depression.relief.depressionissues.admin.meditation.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.meditation.MeditationPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MeditationManageAdminActivity extends AppCompatActivity {
    private ViewPager viewPager_tab;
    private TabLayout tabLayout;
    private FirebaseFirestore firestore;
    private CollectionReference meditationCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_manage_admin);

        viewPager_tab = findViewById(R.id.viewPager_tab);
        tabLayout = findViewById(R.id.tabLayout);

        firestore = FirebaseFirestore.getInstance();
        meditationCollection = firestore.collection("meditations");

        MeditationPagerAdapter pagerAdapter = new MeditationPagerAdapter(getSupportFragmentManager(), 6);
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
        Intent intent = new Intent(MeditationManageAdminActivity.this, MeditationDataAddActivity.class);
        startActivity(intent);
    }
}