package com.depression.relief.depressionissues.specialist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.specialist.fragments.DoctorCommunityFragment;
import com.depression.relief.depressionissues.specialist.fragments.DoctorHomeFragment;
import com.depression.relief.depressionissues.specialist.fragments.DoctorProfileFragment;
import com.depression.relief.depressionissues.specialist.fragments.DoctorSeminarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DoctorHomeActivity extends AppCompatActivity {
    BottomNavigationView doc_bottomnav;
    FrameLayout doctor_frameview;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        doc_bottomnav = findViewById(R.id.doc_bottomnav);
        doctor_frameview = findViewById(R.id.doctor_frameview);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        replaced(new DoctorHomeFragment());


        doc_bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dochomeTab:
                        item.setChecked(true);
                        replaced(new DoctorHomeFragment());
                        break;
                    case R.id.doccommunityTab:
                        item.setChecked(true);
                        replaced(new DoctorCommunityFragment());
                        break;
                    case R.id.docseminarTab:
                        item.setChecked(true);
                        replaced(new DoctorSeminarFragment());
                        break;
                    case R.id.docprofileTab:
                        item.setChecked(true);
                        replaced(new DoctorProfileFragment());
                        break;

                }
                return false;
            }
        });

        // Delay the dismissal of the ProgressDialog after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000);
    }

    private void replaced(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.doctor_frameview, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.INSTANCE.animateSlideDown(this);
    }
}