package com.example.kyler.customedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.StateSet;
import android.widget.EditText;

/**
 * Created by DuckKy on 1/5/2017.
 */

public class CustomEditText extends EditText {
    private final int COLOR_TRANSPARENT_DEFAULT = getResources().getColor(R.color.transparent);
    private final int COLOR_WHITE_DEFAULT = getResources().getColor(R.color.white);
    private final int COLOR_ERROR_DEFAULT = getResources().getColor(R.color.red);

    private int borderColorNormal = COLOR_WHITE_DEFAULT;
    private int borderColorFocus = COLOR_WHITE_DEFAULT;
    private int errorColor = COLOR_ERROR_DEFAULT;
    private int surfaceColor = COLOR_WHITE_DEFAULT;
    private int drawBorderFlag = DrawBorder.NONE;
    private int drawCornerFlag = DrawCorner.CORNER_ALL;
    private int borderWith = 0;
    private int radius = 0;
    private int[] layerInset;
    private StateListDrawable stateListDrawable;
    private LayerDrawable normalBackground;
    private LayerDrawable focusBackground;
    private LayerDrawable errorBackground;
    private float[] underCorner, surfaceCorner;

    public CustomEditText(Context context) {
        this(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typeProperty = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomEditText,
                0, 0);
        try {
            borderWith = (int) typeProperty.getDimension(R.styleable.CustomEditText_borderWidth, 0f);
            radius = (int) typeProperty.getDimension(R.styleable.CustomEditText_radius, 0f);
            drawBorderFlag = typeProperty.getInt(R.styleable.CustomEditText_drawBorder, DrawBorder.NONE);
            drawCornerFlag = typeProperty.getInt(R.styleable.CustomEditText_drawCorner, DrawCorner.CORNER_ALL);
            borderColorNormal = typeProperty.getColor(R.styleable.CustomEditText_borderColorNormal, COLOR_WHITE_DEFAULT);
            borderColorFocus = typeProperty.getColor(R.styleable.CustomEditText_borderColorFocus, COLOR_WHITE_DEFAULT);
            surfaceColor = typeProperty.getColor(R.styleable.CustomEditText_surfaceColor, COLOR_WHITE_DEFAULT);
            errorColor = typeProperty.getColor(R.styleable.CustomEditText_errorColor, COLOR_ERROR_DEFAULT);
        } finally {
            typeProperty.recycle();
        }
        initAttributes();
        initView();
    }

    private void initAttributes() {
        stateListDrawable = getEditTextStateList(drawBorderFlag, drawCornerFlag, radius);
        errorBackground = getLayerDrawableBackground(errorColor);
    }

    public void setDrawBorderFlag(int flag){
        this.drawBorderFlag = flag;
        updateAttributes();
    }

    public void setDrawCornerFlag(int flag){
        this.drawCornerFlag = flag;
        updateAttributes();
    }

    public void setBorderColorNormal(int color){
        borderColorNormal = getResources().getColor(color);
        updateAttributes();
    }

    public void setBorderColorFocus(int color){
        borderColorFocus = getResources().getColor(color);
        updateAttributes();
    }

    public void setErrorColor(int color){
        errorColor = getResources().getColor(color);
        updateAttributes();
    }

    public void setbackgroundColor(int color){
        surfaceColor = getResources().getColor(color);
        updateAttributes();
    }

    private void updateAttributes(){
        stateListDrawable = getEditTextStateList(drawBorderFlag, drawCornerFlag, radius);
    }

    public void addTextChangedListener2(final TextWatcher watcher) {
        TextWatcher newWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                watcher.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    CustomEditText.this.setBackground(focusBackground);
                } else {
                    CustomEditText.this.setBackground(stateListDrawable);
                }
                watcher.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                watcher.afterTextChanged(s);
            }
        };
        CustomEditText.this.removeTextChangedListener(textWatcher);
        CustomEditText.this.addTextChangedListener(newWatcher);
    }

    private void initView() {
        CustomEditText.this.setBackground(stateListDrawable);
        CustomEditText.this.addTextChangedListener(textWatcher);
    }

    public void setError() {
        CustomEditText.this.setBackground(errorBackground);
    }

    private StateListDrawable getEditTextStateList(int drawBorderFlag, int drawCornerFlag, float radius) {
        initCorner(drawCornerFlag, radius);
        layerInset = getLayerInset(drawBorderFlag);
        normalBackground = getLayerDrawableBackground(borderColorNormal);
        focusBackground = getLayerDrawableBackground(borderColorFocus);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, focusBackground);
        stateListDrawable.addState(new int[]{android.R.attr.cursorVisible}, focusBackground);
        stateListDrawable.addState(StateSet.WILD_CARD, normalBackground);
        return stateListDrawable;
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                CustomEditText.this.setBackground(focusBackground);
            } else {
                CustomEditText.this.setBackground(stateListDrawable);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private LayerDrawable getLayerDrawableBackground(int borderColor) {
        RoundRectShape underLayerRoundRectShape = new RoundRectShape(underCorner, null, null);
        ShapeDrawable underLayer = new ShapeDrawable(underLayerRoundRectShape);
        underLayer.getPaint().setColor(borderColor);

        RoundRectShape surfaceLayerRoundRectShape = new RoundRectShape(surfaceCorner, null, null);
        ShapeDrawable surfaceLayer = new ShapeDrawable(surfaceLayerRoundRectShape);
        surfaceLayer.getPaint().setColor(surfaceColor);

        Drawable[] layers = {underLayer, surfaceLayer};
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        layerDrawable.setLayerInset(0, 0, 0, 0, 0);
        layerDrawable.setLayerInset(1, layerInset[0], layerInset[1], layerInset[2], layerInset[3]);

        return layerDrawable;
    }

    private void initCorner(int flag, float radius) {
        float minus = radius - borderWith;
        float surfaceRadius = (minus < (radius / 2)) ? (radius / 2) : minus;
        switch (flag) {
            case DrawCorner.CORNER_NONE:
                surfaceCorner = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
                underCorner = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
                break;
            case DrawCorner.CORNER_LEFT_TOP:
                underCorner = new float[]{radius, radius, 0, 0, 0, 0, 0, 0};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, 0, 0, 0, 0, 0, 0};
                break;
            case DrawCorner.CORNER_BOTTOM_LEFT:
                underCorner = new float[]{0, 0, 0, 0, 0, 0, radius, radius};
                surfaceCorner = new float[]{0, 0, 0, 0, 0, 0, surfaceRadius, surfaceRadius};
                break;
            case DrawCorner.CORNER_TOP_RIGHT:
                underCorner = new float[]{0, 0, radius, radius, 0, 0, 0, 0};
                surfaceCorner = new float[]{0, 0, surfaceRadius, surfaceRadius, 0, 0, 0, 0};
                break;
            case DrawCorner.CORNER_RIGHT_BOTTOM:
                underCorner = new float[]{0, 0, 0, 0, radius, radius, 0, 0};
                surfaceCorner = new float[]{0, 0, 0, 0, surfaceRadius, surfaceRadius, 0, 0};
                break;
            case DrawCorner.CORNER_LEFT_TOP_TOP_RIGHT:
                underCorner = new float[]{radius, radius, radius, radius, 0, 0, 0, 0};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, 0, 0, 0, 0};
                break;
            case DrawCorner.CORNER_LEFT_TOP_BOTTOM_LEFT:
                underCorner = new float[]{radius, radius, 0, 0, 0, 0, radius, radius};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, 0, 0, 0, 0, surfaceRadius, surfaceRadius};
                break;
            case DrawCorner.CORNER_RIGHT_BOTTOM_BOTTOM_LEFT:
                underCorner = new float[]{0, 0, 0, 0, radius, radius, radius, radius};
                surfaceCorner = new float[]{0, 0, 0, 0, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius};
                break;
            case DrawCorner.CORNER_TOP_RIGHT_RIGHT_BOTTOM:
                underCorner = new float[]{0, 0, radius, radius, radius, radius, 0, 0};
                surfaceCorner = new float[]{0, 0, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, 0, 0};
                break;
            case DrawCorner.CORNER_LEFT_TOP_TOP_RIGHT_RIGHT_BOTTOM:
                underCorner = new float[]{radius, radius, radius, radius, radius, radius, 0, 0};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, 0, 0};
                break;
            case DrawCorner.CORNER_BOTTOM_LEFT_LEFT_TOP_TOP_RIGHT:
                underCorner = new float[]{radius, radius, radius, radius, 0, 0, radius, radius};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, 0, 0, surfaceRadius, surfaceRadius};
                break;
            case DrawCorner.CORNER_RIGHT_BOTTOM_BOTTOM_LEFT_LEFT_TOP:
                underCorner = new float[]{radius, radius, 0, 0, radius, radius, radius, radius};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, 0, 0, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius};
                break;
            case DrawCorner.CORNER_TOP_RIGHT_RIGHT_BOTTOM_BOTTOM_LEFT:
                underCorner = new float[]{0, 0, radius, radius, radius, radius, radius, radius};
                surfaceCorner = new float[]{0, 0, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius};
                break;
            case DrawCorner.CORNER_LEFT_TOP_RIGHT_BOTTOM:
                underCorner = new float[]{radius, radius, 0, 0, radius, radius, 0, 0};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, 0, 0, surfaceRadius, surfaceRadius, 0, 0};
                break;
            case DrawCorner.CORNER_BOTTOM_LEFT_TOP_RIGHT:
                underCorner = new float[]{0, 0, radius, radius, 0, 0, radius, radius};
                surfaceCorner = new float[]{0, 0, surfaceRadius, surfaceRadius, 0, 0, surfaceRadius, surfaceRadius};
                break;
            case DrawCorner.CORNER_ALL:
                underCorner = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius};
                break;
            default:
                surfaceCorner = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
                underCorner = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
                break;
        }
    }

    private int[] getLayerInset(int flag) {
        int[] layerLeftTopRightBottom;
        switch (flag) {
            case DrawBorder.LEFT:
                layerLeftTopRightBottom = new int[]{borderWith, 0, 0, 0};
                break;
            case DrawBorder.TOP:
                layerLeftTopRightBottom = new int[]{0, borderWith, 0, 0};
                break;
            case DrawBorder.RIGHT:
                layerLeftTopRightBottom = new int[]{0, 0, borderWith, 0};
                break;
            case DrawBorder.BOTTOM:
                layerLeftTopRightBottom = new int[]{0, 0, 0, borderWith};
                break;
            case DrawBorder.LEFT_TOP:
                layerLeftTopRightBottom = new int[]{borderWith, borderWith, 0, 0};
                break;
            case DrawBorder.LEFT_RIGHT:
                layerLeftTopRightBottom = new int[]{borderWith, 0, borderWith, 0};
                break;
            case DrawBorder.LEFT_BOTTOM:
                layerLeftTopRightBottom = new int[]{borderWith, 0, 0, borderWith};
                break;
            case DrawBorder.TOP_RIGHT:
                layerLeftTopRightBottom = new int[]{0, borderWith, borderWith, 0};
                break;
            case DrawBorder.TOP_BOTTOM:
                layerLeftTopRightBottom = new int[]{0, borderWith, 0, borderWith};
                break;
            case DrawBorder.RIGHT_BOTTOM:
                layerLeftTopRightBottom = new int[]{0, 0, borderWith, borderWith};
                break;
            case DrawBorder.LEFT_TOP_RIGHT:
                layerLeftTopRightBottom = new int[]{borderWith, borderWith, borderWith, 0};
                break;
            case DrawBorder.LEFT_TOP_BOTTOM:
                layerLeftTopRightBottom = new int[]{borderWith, borderWith, 0, borderWith};
                break;
            case DrawBorder.LEFT_RIGHT_BOTTOM:
                layerLeftTopRightBottom = new int[]{borderWith, 0, borderWith, borderWith};
                break;
            case DrawBorder.TOP_RIGHT_BOTTOM:
                layerLeftTopRightBottom = new int[]{0, borderWith, borderWith, borderWith};
                break;
            case DrawBorder.ALL:
                layerLeftTopRightBottom = new int[]{borderWith, borderWith, borderWith, borderWith};
                break;
            case DrawBorder.NONE:
            default:
                layerLeftTopRightBottom = new int[]{0, 0, 0, 0};
                break;
        }
        return layerLeftTopRightBottom;
    }
}
