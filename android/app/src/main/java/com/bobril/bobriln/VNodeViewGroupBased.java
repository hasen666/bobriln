package com.bobril.bobriln;

import android.view.ViewGroup;

import com.facebook.csslayout.CSSLayout;
import com.facebook.csslayout.CSSLayoutContext;
import com.facebook.csslayout.CSSNode;

public abstract class VNodeViewGroupBased extends VNodeViewBased {

    @Override
    int validateView(int indexInParent) {
        boolean wasNeed = needValidate;
        int res = super.validateView(indexInParent);
        if (wasNeed)
        {
            int idx = 0;
            if (children!=null) {
                for (int i=0;i<children.size();i++) {
                    idx = children.get(i).validateView(idx);
                }
            }
        }
        return res;
    }

    @Override
    public void doLayout(CSSLayoutContext ctx) {
        if (children!=null) {
            for (int i=0;i<children.size();i++) {
                children.get(i).doLayout(ctx);
            }
        }
        super.doLayout(ctx);
    }

    @Override
    public void flushLayout() {
        if (children!=null) {
            for (int i=0;i<children.size();i++) {
                children.get(i).flushLayout();
            }
        }
        super.flushLayout();
    }

    public ViewGroup getViewForChildren() {
        return (ViewGroup)view;
    }
    public CSSNode getCssForChildren() { return css; }
}
