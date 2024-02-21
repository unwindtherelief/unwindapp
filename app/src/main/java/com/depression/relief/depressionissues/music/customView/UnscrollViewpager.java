package com.depression.relief.depressionissues.music.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class UnscrollViewpager extends ViewPager {
    private boolean isTouch = false;

    public UnscrollViewpager(Context context) {
        super(context);
    }

    public UnscrollViewpager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.isTouch && super.onTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.isTouch && super.onInterceptTouchEvent(motionEvent);
    }

    public void setPagingEnabled(boolean z) {
        this.isTouch = z;
    }

    public boolean canScrollHorizontally(int i) {
        if (!this.isTouch) {
            return false;
        }
        return super.canScrollHorizontally(i);
    }
}
