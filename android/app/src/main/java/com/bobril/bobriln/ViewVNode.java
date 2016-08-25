package com.bobril.bobriln;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.Objects;

public class ViewVNode extends ViewGroupBasedVNode {
    @Override
    View createView(Context ctx) {
        View view = new ViewView(ctx);
        updateBackground(view, getStyle().get("background"));
        return view;
    }

    private void updateBackground(View view, Object background) {
        view.setBackgroundColor(ColorUtils.toColor(background));
    }

    @Override
    VNode createByTag(String tag) {
        return lparent.createByTag(tag);
    }

    @Override
    public void setStyle(String styleName, Object styleValue) {
        if (view!=null) {
            if (Objects.equals(styleName, "background")) {
                updateBackground(view, styleValue);
            }
        }
        super.setStyle(styleName, styleValue);
    }

    @Override
    void setStringChild(String content) {
        if (content==null) {
            this.content=null;
            children=null;
            if (view!=null) ((ViewView)view).removeAllViews();
            css.dirty();
            return;
        }
        this.content = content;
        VNode n=createByTag("Text");
        n.vdom = this.vdom;
        n.tag = "Text";
        n.nodeId = -2;
        n.lparent = this;
        insertBefore(n, null);
        n.setStringChild(content);
    }
}