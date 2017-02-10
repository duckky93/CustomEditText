package com.example.kyler.customedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyler on 2/10/17.
 */
public class GroupEditTextCustom extends LinearLayout {

    private final int CORLOR_NORMAL_DEFAULT = getResources().getColor(R.color.gray);
    private int normalColor;
    private int focusColor;
    private int errorColor;
    private int normalLayer;
    private int errorLayer;
    private int borderNormalWidth;
    private int borderErrorWidth;

    List<EditTextCustomable> listEdt;

    public GroupEditTextCustom(Context context) {
        this(context, null);
    }

    public GroupEditTextCustom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupEditTextCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_custom_edit_text, this, true);
        TypedArray typeProperty = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EditTextCustomable,
                0, 0);
        try {
            normalColor = typeProperty.getColor(R.styleable.EditTextCustomable_borderNormalColor, CORLOR_NORMAL_DEFAULT);
            focusColor = typeProperty.getColor(R.styleable.EditTextCustomable_borderFocusColor, CORLOR_NORMAL_DEFAULT);
            errorColor = typeProperty.getColor(R.styleable.EditTextCustomable_borderErrorColor, CORLOR_NORMAL_DEFAULT);
            normalLayer = typeProperty.getInt(R.styleable.EditTextCustomable_drawNormalBorder, DrawBorder.NONE);
            errorLayer = typeProperty.getInt(R.styleable.EditTextCustomable_drawErrorBorder, DrawBorder.NONE);
            borderNormalWidth = typeProperty.getDimensionPixelSize(R.styleable.EditTextCustomable_borderNormalWidth, 0);
            borderErrorWidth = typeProperty.getDimensionPixelSize(R.styleable.EditTextCustomable_borderErrorWidth, 0);
        } finally {
            typeProperty.recycle();
        }
        initViews(view);
    }

    private void initViews(View view) {
        listEdt = new ArrayList<>();
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt1));
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt2));
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt3));
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt4));
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt5));
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt6));
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt7));
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt8));
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt9));
        listEdt.add((EditTextCustomable) view.findViewById(R.id.edt10));
        initListEdittext();
    }

    private void initListEdittext() {
        for (EditTextCustomable edt : listEdt) {
            edt.setNormalColor(normalColor);
            edt.setFocusColor(focusColor);
            edt.setErrorColor(errorColor);
            edt.setBorderNormalWidth(borderNormalWidth);
            edt.setBorderErrorWidth(borderErrorWidth);
        }
    }
}
