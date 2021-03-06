package com.bobril.bobriln;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;

import com.facebook.csslayout.CSSNode;

public class NViewView extends ViewGroup {
    VNodeViewBased owner;
    boolean noDecoration;

    public NViewView(Context context, VNodeViewBased owner, boolean noDecoration) {
        super(context);
        this.owner = owner;
        this.noDecoration = noDecoration;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        ViewDecoration decoration = owner.getDecoration();
        if (noDecoration) decoration = null;
        if (decoration != null) decoration.onBeforeDraw(canvas);
        super.dispatchDraw(canvas);
        if (decoration != null) decoration.onAfterDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int w, int h) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lpo = child.getLayoutParams();
            if (lpo instanceof AbsoluteLayout.LayoutParams) {
                AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) lpo;
                child.layout(lp.x, lp.y, lp.x + lp.width, lp.y + lp.height);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lpo = child.getLayoutParams();
            if (lpo instanceof AbsoluteLayout.LayoutParams) {
                AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) lpo;
                child.measure(MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY));
            }
        }
        CSSNode css = null;
        if (this.owner instanceof VNodeViewGroupBased) {
            css = ((VNodeViewGroupBased)this.owner).getCssForChildren();
        } else {
            css = this.owner.css;
        }
        this.setMeasuredDimension(Math.round(css.getLayoutWidth()), Math.round(css.getLayoutHeight()));
    }
}
