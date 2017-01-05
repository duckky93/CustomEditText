package com.example.kyler.customedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import butterknife.BindView;

/**
 * Created by DuckKy on 1/5/2017.
 */

public class CustomEditText extends LinearLayout {
    private final int BORDER_NONE = 0;
    private final int BORDER_TOP = 1;
    private final int BORDER_BOTTOM = 2;
    private final int BORDER_LEFT = 4;
    private final int BORDER_RIGHT = 8;
    private final int BORDER_TOP_BOTTOM = 3;
    private final int BORDER_TOP_LEFT = 5;
    private final int BORDER_TOP_RIGHT = 6;
    private final int BORDER_BOTTOM_RIGHT = 10;
    private final int BORDER_LEFT_RIGHT = 12;
    private final int BORDER_TOP_BOTTOM_LEFT = 9;
    private final int BORDER_TOP_BOTTOM_RIGHT = 11;
    private final int BORDER_TOP_LEFT_RIGHT = 13;
    private final int BORDER_BOTTOM_LEFT_RIGHT = 14;
    private final int BORDER_ALL = 15;

    public CustomEditText(Context context) {
        this(context,null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomEditText,
                0, 0);

        try {

        } finally {
            a.recycle();
        }
    }

}
