package com.depression.relief.depressionissues.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.activities.MeditationOfEachActivity;

public class MeditationFragment extends Fragment {
    ImageView img_meditation_basic, img_success, img_better_sleep, img_relationship, img_personal_growth, img_focus, img_stress_relief;

    public MeditationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meditation, container, false);

        img_meditation_basic = view.findViewById(R.id.img_meditation_basic);
        img_success = view.findViewById(R.id.img_success);
        img_better_sleep = view.findViewById(R.id.img_better_sleep);
        img_relationship = view.findViewById(R.id.img_relationship);
        img_personal_growth = view.findViewById(R.id.img_personal_growth);
        img_focus = view.findViewById(R.id.img_focus);
        img_stress_relief = view.findViewById(R.id.img_stress_relief);


        img_meditation_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfEachActivity.class);
                intent.putExtra("category", "Meditation basic");
                startActivity(intent);
            }
        });

        img_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfEachActivity.class);
                intent.putExtra("category", "Success");
                startActivity(intent);
            }
        });


        img_better_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfEachActivity.class);
                intent.putExtra("category", "Better Sleep");
                startActivity(intent);
            }
        });

        img_relationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfEachActivity.class);
                intent.putExtra("category", "Relationship");
                startActivity(intent);
            }
        });

        img_personal_growth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfEachActivity.class);
                intent.putExtra("category", "Personal Growth");
                startActivity(intent);
            }
        });

        img_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfEachActivity.class);
                intent.putExtra("category", "Focus");
                startActivity(intent);
            }
        });

        img_stress_relief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfEachActivity.class);
                intent.putExtra("category", "Stress relief");
                startActivity(intent);
            }
        });
        return view;
    }
}