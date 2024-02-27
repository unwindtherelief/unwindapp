package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.adapters.AffermationDeckAdapter;
import com.depression.relief.depressionissues.models.AffermationDataModel;
import com.yalantis.library.Koloda;

import java.util.ArrayList;

public class AffermationTinderActivity extends AppCompatActivity {
    private ArrayList<AffermationDataModel> affermationDataList;
    private AffermationDeckAdapter adapter;
    Koloda koloda;
    private int currentPosition = 0;
    ImageView backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affermation_tinder);

        affermationDataList = new ArrayList<>();
        koloda = findViewById(R.id.koloda);
        backbtn = findViewById(R.id.backbtn);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // on below line we are adding data to our array list.
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_1, R.drawable.ic_bg_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_2, R.drawable.ic_bg2_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_3, R.drawable.ic_bg3_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_4, R.drawable.ic_bg4_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_5, R.drawable.ic_bg5_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_6, R.drawable.ic_bg6_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_7, R.drawable.ic_bg_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_8, R.drawable.ic_bg2_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_9, R.drawable.ic_bg3_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_10, R.drawable.ic_bg4_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_11, R.drawable.ic_bg5_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_12, R.drawable.ic_bg_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_13, R.drawable.ic_bg3_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_14, R.drawable.ic_bg4_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_15, R.drawable.ic_bg5_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_16, R.drawable.ic_bg6_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_17, R.drawable.ic_bg_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_18, R.drawable.ic_bg2_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_19, R.drawable.ic_bg3_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_20, R.drawable.ic_bg4_affer));
        affermationDataList.add(new AffermationDataModel(R.string.affermation_str_21, R.drawable.ic_bg5_affer));

        adapter = new AffermationDeckAdapter(affermationDataList, this);
        koloda.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateCard(this);
    }
}