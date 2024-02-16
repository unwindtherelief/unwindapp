package com.depression.relief.depressionissues.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.depression.relief.depressionissues.fragments.TrendingCommentFragment;

public class CommunityPagerAdapter extends FragmentPagerAdapter {

    public CommunityPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Implement logic to return the corresponding fragment based on position
        switch (position) {
            case 0:
                return new TrendingCommentFragment();
            case 1:
                return new TrendingCommentFragment();
            default:
                return new TrendingCommentFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Trending";
            case 1:
                return "Relationship";
            default:
                return null;
        }
    }
}