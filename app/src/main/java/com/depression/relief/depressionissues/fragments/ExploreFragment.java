package com.depression.relief.depressionissues.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.activities.AffermationTinderActivity;
import com.depression.relief.depressionissues.activities.GamesListActivity;
import com.depression.relief.depressionissues.activities.JournalActivity;
import com.depression.relief.depressionissues.music.MusicExploreActivity;

public class ExploreFragment extends Fragment {
    ScrollView explorescroll;
    Animation animation;
    ImageView btn_dailyjournal;
    ImageView btn_dailyaffermation;
    ImageView btn_personaltherapy;
    ImageView btn_musictreat;
    ImageView btn_relaxgames;
    ImageView btn_mindfulbreath;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        explorescroll = view.findViewById(R.id.explorescroll);
        btn_dailyjournal = view.findViewById(R.id.btn_dailyjournal);
        btn_dailyaffermation = view.findViewById(R.id.btn_dailyaffermation);
        btn_personaltherapy = view.findViewById(R.id.btn_personaltherapy);
        btn_musictreat = view.findViewById(R.id.btn_musictreat);
        btn_relaxgames = view.findViewById(R.id.btn_relaxgames);
        btn_mindfulbreath = view.findViewById(R.id.btn_mindfulbreath);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_bottom);

        btn_dailyjournal.setAnimation(animation);
        btn_dailyaffermation.setAnimation(animation);
        btn_personaltherapy.setAnimation(animation);
        btn_musictreat.setAnimation(animation);
        btn_relaxgames.setAnimation(animation);
        btn_mindfulbreath.setAnimation(animation);


        btn_dailyjournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JournalActivity.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(getActivity());
            }
        });
        btn_dailyaffermation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AffermationTinderActivity.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(getActivity());
            }
        });

        btn_musictreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MusicExploreActivity.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(getActivity());
            }
        });
        btn_relaxgames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GamesListActivity.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(getActivity());
            }
        });


        return view;
    }
}