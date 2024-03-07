package com.depression.relief.depressionissues.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.doctor.DoctorAddActivity;
import com.depression.relief.depressionissues.admin.meditation.MeditationCategoryChooseActivity;
import com.depression.relief.depressionissues.admin.quetsions.QuestionAdapter;
import com.depression.relief.depressionissues.admin.quetsions.QuestionData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    ImageView btn_assessment, btn_usermanagement, btn_doctor_management, btn_event_management, btn_content_operation, btn_meditation_management;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btn_assessment = findViewById(R.id.btn_assessment);
        btn_usermanagement = findViewById(R.id.btn_usermanagement);
        btn_doctor_management = findViewById(R.id.btn_doctor_management);
        btn_event_management = findViewById(R.id.btn_event_management);
        btn_content_operation = findViewById(R.id.btn_content_operation);
        btn_meditation_management = findViewById(R.id.btn_meditation_management);

        animation = AnimationUtils.loadAnimation(this, R.anim.slide_bottom);

        btn_assessment.setAnimation(animation);
        btn_usermanagement.setAnimation(animation);
        btn_doctor_management.setAnimation(animation);
        btn_event_management.setAnimation(animation);
        btn_content_operation.setAnimation(animation);
        btn_meditation_management.setAnimation(animation);

        btn_doctor_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, DoctorAddActivity.class);
                startActivity(intent);
            }
        });


        btn_meditation_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, MeditationCategoryChooseActivity.class);
                startActivity(intent);
            }
        });


        btn_event_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, EventManagementActivity.class);
                startActivity(intent);
            }
        });

        btn_usermanagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, UserManagementActivity.class);
                startActivity(intent);
            }
        });
        btn_assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AssessmentAdminActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideDown(this);
    }
}