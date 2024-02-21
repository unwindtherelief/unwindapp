package com.depression.relief.depressionissues.music.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class UnscrollableRecyclerview extends RecyclerView {
    public UnscrollableRecyclerview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 2) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return false;
    }
}
