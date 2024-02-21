package com.depression.relief.depressionissues.music.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MakeRefreshing extends SwipeRefreshLayout {
    private boolean isDisabled;
    private float mPrevX;
    private int mTouchSlop;

    public MakeRefreshing(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void disableInterceptTouchEvent(boolean z) {
        this.isDisabled = z;
        getParent().requestDisallowInterceptTouchEvent(z);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mPrevX = MotionEvent.obtain(motionEvent).getX();
        } else if (action == 2 && (this.isDisabled || Math.abs(motionEvent.getX() - this.mPrevX) > ((float) this.mTouchSlop))) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
