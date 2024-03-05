package com.depression.relief.depressionissues.admin.meditation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.depression.relief.depressionissues.admin.meditation.fragments.QuickFocusFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.QuickMotivationFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.QuickPeaceFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.QuickPersonalGrowthFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.QuickSelfHealingFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.QuickStressReliefFragment;

public class QuickMeditationPagerAdapter extends FragmentStatePagerAdapter {

    private final int tabCount;

    public QuickMeditationPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new QuickPersonalGrowthFragment();
            case 1:
                return new QuickMotivationFragment();
            case 2:
                return new QuickSelfHealingFragment();
            case 3:
                return new QuickStressReliefFragment();
            case 4:
                return new QuickPeaceFragment();
            case 5:
                return new QuickFocusFragment();
            default:
                return new QuickPersonalGrowthFragment();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Personal Growth";
            case 1:
                return "Motivation";
            case 2:
                return "Self Healing";
            case 3:
                return "Stress Relief";
            case 4:
                return "Peace";
            case 5:
                return "Focus";
            default:
                return null;
        }
    }
}