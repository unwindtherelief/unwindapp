package com.depression.relief.depressionissues.admin.meditation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.depression.relief.depressionissues.admin.meditation.fragments.AllMeditationFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.BetterSleepMeditationFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.FocusMeditationFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.MeditationBasicFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.PersonalGrowthMeditationFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.RelationshipMeditationFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.StressReliefMeditationFragment;
import com.depression.relief.depressionissues.admin.meditation.fragments.SuccessMeditationFragment;

public class MeditationPagerAdapter extends FragmentStatePagerAdapter {

    private final int tabCount;

    public MeditationPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllMeditationFragment();
            case 1:
                return new MeditationBasicFragment();
            case 2:
                return new SuccessMeditationFragment();
            case 3:
                return new BetterSleepMeditationFragment();
            case 4:
                return new RelationshipMeditationFragment();
            case 5:
                return new PersonalGrowthMeditationFragment();
            case 6:
                return new FocusMeditationFragment();
            case 7:
                return new StressReliefMeditationFragment();
            default:
                return new AllMeditationFragment();
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
                return "All";
            case 1:
                return "Meditation Basic";
            case 2:
                return "Success";
            case 3:
                return "Basic Sleep";
            case 4:
                return "Relationship";
            case 5:
                return "Personal Growth";
            case 6:
                return "Focus";
            case 7:
                return "Stress Relief";
            default:
                return null;
        }
    }
}
