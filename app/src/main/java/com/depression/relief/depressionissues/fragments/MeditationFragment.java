package com.depression.relief.depressionissues.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.activities.BrethingExerciseActvity;
import com.depression.relief.depressionissues.activities.MeditationOfEachActivity;
import com.depression.relief.depressionissues.activities.MeditationOfQuickEachActivity;

public class MeditationFragment extends Fragment {
    ImageView img_meditation_basic, img_success, img_better_sleep, img_relationship, img_personal_growth, img_focus, img_stress_relief;
    ImageView btn_quick_personalGrowth, btn_quick_motivation, btn_quick_selfheal, btn_quick_stressrelief, btn_quick_peace, btn_quick_focus;
    ImageView btn_brething_excercise, btn_chant_counter;

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


        btn_quick_personalGrowth = view.findViewById(R.id.btn_quick_personalGrowth);
        btn_quick_motivation = view.findViewById(R.id.btn_quick_motivation);
        btn_quick_selfheal = view.findViewById(R.id.btn_quick_selfheal);
        btn_quick_stressrelief = view.findViewById(R.id.btn_quick_stressrelief);
        btn_quick_peace = view.findViewById(R.id.btn_quick_peace);
        btn_quick_focus = view.findViewById(R.id.btn_quick_focus);

        btn_brething_excercise = view.findViewById(R.id.btn_brething_excercise);
        btn_chant_counter = view.findViewById(R.id.btn_chant_counter);


        btn_brething_excercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BrethingExerciseActvity.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateCard(getActivity());
            }
        });


        //starts here quick meditatio data passing
        btn_quick_personalGrowth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfQuickEachActivity.class);
                intent.putExtra("category", "Personal Growth");
                startActivity(intent);
            }
        });

        btn_quick_motivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfQuickEachActivity.class);
                intent.putExtra("category", "Motivation");
                startActivity(intent);
            }
        });


        btn_quick_selfheal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfQuickEachActivity.class);
                intent.putExtra("category", "Self Healing");
                startActivity(intent);
            }
        });


        btn_quick_stressrelief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfQuickEachActivity.class);
                intent.putExtra("category", "Stress Relief");
                startActivity(intent);
            }
        });


        btn_quick_peace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfQuickEachActivity.class);
                intent.putExtra("category", "Peace");
                startActivity(intent);
            }
        });


        btn_quick_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeditationOfQuickEachActivity.class);
                intent.putExtra("category", "Focus");
                startActivity(intent);
            }
        });


        //starts here guided meditation data passing

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