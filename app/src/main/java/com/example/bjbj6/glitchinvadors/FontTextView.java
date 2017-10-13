package com.example.bjbj6.glitchinvadors;

import android.widget.TextView;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Typeface;


/**
 * Created by bjbj6 on 2017-09-29.
 */

public class FontTextView extends android.support.v7.widget.AppCompatTextView{
    public FontTextView(Context context) {
        super(context);
        setFont();
    }

    public FontTextView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setFont();
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/soya.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
