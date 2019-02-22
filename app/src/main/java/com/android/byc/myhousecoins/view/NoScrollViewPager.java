package com.android.byc.myhousecoins.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 12:53
 * @description
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isCanScorll = false;
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScorll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }

    }
    @Override
    public boolean onInterceptTouchEvent (MotionEvent ev) {
        if (isCanScorll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }
*/

}
