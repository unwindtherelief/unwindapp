package com.depression.relief.depressionissues.launch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.AdminActivity;

public class ChooseProfessionActivity extends AppCompatActivity {
    ImageView btn_admingo, btn_usergo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profession);

        btn_admingo = findViewById(R.id.btn_admingo);
        btn_usergo = findViewById(R.id.btn_usergo);

        btn_admingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_admingo.setImageResource(R.drawable.ic_admin_profession_clicked);
                Intent intent = new Intent(ChooseProfessionActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        btn_usergo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_usergo.setImageResource(R.drawable.ic_user_profession_clicked);
                Intent intent = new Intent(ChooseProfessionActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.INSTANCE.animateShrink(this);
    }
}