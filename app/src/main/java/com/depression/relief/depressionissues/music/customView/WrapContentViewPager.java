package com.depression.relief.depressionissues.music.customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class WrapContentViewPager extends ViewPager {
    /* access modifiers changed from: private */
    public static final String TAG = "WrapContentViewPager";
    private boolean animateHeight;
    private int decorHeight = 0;
    /* access modifiers changed from: private */
    public int height = 0;
    private int leftHeight;
    private int rightHeight;
    private int scrollingPosition = -1;
    private int widthMeasuredSpec;

    public WrapContentViewPager(Context context) {
        super(context);
        initialized();
    }

    public WrapContentViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialized();
    }

    private void initialized() {
        addOnPageChangeListener(new OnPageChangeListener() {
            public int state;

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                if (this.state == 0) {
                    int unused = WrapContentViewPager.this.height = 0;
                    String access$100 = WrapContentViewPager.TAG;
                    Log.d(access$100, "onPageSelected:" + i);
                }
            }

            public void onPageScrollStateChanged(int i) {
                this.state = i;
            }
        });
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        if (pagerAdapter instanceof ThingsAtPlasceInterface) {
            this.height = 0;
            super.setAdapter(pagerAdapter);
            return;
        }
        throw new IllegalArgumentException("WrapContentViewPage requires that PagerAdapter will implement ObjectAtPositionInterface");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.widthMeasuredSpec = i;
        int mode = MeasureSpec.getMode(i2);
        if (mode == 0 || mode == Integer.MIN_VALUE) {
            if (this.height == 0) {
                this.decorHeight = 0;
                for (int i3 = 0; i3 < getChildCount(); i3++) {
                    View childAt = getChildAt(i3);
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    if (layoutParams != null && layoutParams.isDecor) {
                        int i4 = layoutParams.gravity & 112;
                        if (i4 == 48 || i4 == 80) {
                            this.decorHeight += childAt.getMeasuredHeight();
                        }
                    }
                }
                View viewAtPosition = getViewAtPosition(getCurrentItem());
                if (viewAtPosition != null) {
                    this.height = measureViewHeight(viewAtPosition);
                }
                Log.d(TAG, "onMeasure height:" + this.height + " decor:" + this.decorHeight);
            }
            int paddingBottom = this.height + this.decorHeight + getPaddingBottom() + getPaddingTop();
            @SuppressLint("WrongConstant") int makeMeasureSpec = MeasureSpec.makeMeasureSpec(paddingBottom, BasicMeasure.EXACTLY);
            Log.d(TAG, "onMeasure total height:" + paddingBottom);
            i2 = makeMeasureSpec;
        }
        super.onMeasure(i, i2);
    }

    public void onPageScrolled(int i, float f, int i2) {
        int i3;
        super.onPageScrolled(i, f, i2);
        if (this.scrollingPosition != i) {
            this.scrollingPosition = i;
            View viewAtPosition = getViewAtPosition(i);
            View viewAtPosition2 = getViewAtPosition(i + 1);
            if (viewAtPosition == null || viewAtPosition2 == null) {
                this.animateHeight = false;
            } else {
                this.leftHeight = measureViewHeight(viewAtPosition);
                this.rightHeight = measureViewHeight(viewAtPosition2);
                this.animateHeight = true;
                String str = TAG;
                Log.d(str, "onPageScrolled heights left:" + this.leftHeight + " right:" + this.rightHeight);
            }
        }
        if (this.animateHeight && this.height != (i3 = (int) ((((float) this.leftHeight) * (1.0f - f)) + (((float) this.rightHeight) * f)))) {
            String str2 = TAG;
            Log.d(str2, "onPageScrolled height change:" + i3);
            this.height = i3;
            requestLayout();
            invalidate();
        }
    }

    @SuppressLint("WrongConstant")
    private int measureViewHeight(View view) {
        view.measure(getChildMeasureSpec(this.widthMeasuredSpec, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), MeasureSpec.makeMeasureSpec(0, 0));
        return view.getMeasuredHeight();
    }

    /* access modifiers changed from: protected */
    public View getViewAtPosition(int i) {
        Object objectAtPosition;
        if (getAdapter() == null || (objectAtPosition = ((ThingsAtPlasceInterface) getAdapter()).getObjectAtPosition(i)) == null) {
            return null;
        }
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt != null && getAdapter().isViewFromObject(childAt, objectAtPosition)) {
                return childAt;
            }
        }
        return null;
    }
}
