package com.example.kyler.customedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by kyler on 2/10/17.
 */
public class EditTextCustomable extends EditText {

    int normalColor;
    int focusColor;
    int errorColor;
    int normalLayer;
    int errorLayer;
    int borderNormalWidth;
    int borderErrorWidth;


    private final int CORLOR_NORMAL_DEFAULT = getResources().getColor(R.color.main);

    public EditTextCustomable(Context context) {
        this(context, null);
    }

    public EditTextCustomable(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public EditTextCustomable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        setBackground(getLayerDrawableBackground(normalLayer, normalColor));
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = getResources().getColor(normalColor);
    }

    public void setFocusColor(int focusColor) {
        this.focusColor = getResources().getColor(focusColor);
    }

    public void setErrorColor(int errorColor) {
        this.errorColor = getResources().getColor(errorColor);
    }

    public void setBorderNormalWidth(int px) {
        this.borderNormalWidth = px;
    }

    public void setBorderErrorWidth(int px) {
        this.borderErrorWidth = px;
    }

    public void setFocusBackground(int drawFocusBorder) {
        setBackground(getLayerDrawableBackground(DrawBorder.NONE, normalColor, drawFocusBorder, focusColor));
    }

    public void setNormalBackground(int drawNormalBorder) {
        setBackground(getLayerDrawableBackground(drawNormalBorder, normalColor));
    }

    public void setErrorBackground(int drawErrorBorder) {
        setBackground(getLayerDrawableBackground(drawErrorBorder, errorColor));
    }

    public void setErrorBackground(int drawNormalBorder, int drawErrorBorder) {
        setBackground(getLayerDrawableBackground(drawNormalBorder, normalColor, drawErrorBorder, errorColor));
    }

    private LayerDrawable getLayerDrawableBackground(int flagLayer1, int layer1Color) {
        return getLayerDrawableBackground(flagLayer1, layer1Color, DrawBorder.NONE, normalColor);
    }

    private LayerDrawable getLayerDrawableBackground(int flagLayer1, int layer1Color, int flagLayer2, int layer2Color) {
        int[] layer1Inset = getLayerInset(flagLayer1, borderNormalWidth);
        GradientDrawable layer1 = new GradientDrawable();
        layer1.setStroke(borderNormalWidth, layer1Color);

        int[] layer2Inset = getLayerInset(flagLayer2, borderErrorWidth);
        GradientDrawable layer2 = new GradientDrawable();
        layer2.setStroke(borderErrorWidth, layer2Color);
        Drawable[] layers = new Drawable[]{layer1, layer2};
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setLayerInset(0, layer1Inset[0], layer1Inset[1], layer1Inset[2], layer1Inset[3]);
        layerDrawable.setLayerInset(1, layer2Inset[0], layer2Inset[1], layer2Inset[2], layer2Inset[3]);
        return layerDrawable;
    }

    private int[] getLayerInset(int flag, int borderWith) {
        int negativeBoder = borderWith * -1 - 10;
        int[] layerLeftTopRightBottom;
        switch (flag) {
            case DrawBorder.LEFT:
                layerLeftTopRightBottom = new int[]{0, negativeBoder, negativeBoder, negativeBoder};
                break;
            case DrawBorder.TOP:
                layerLeftTopRightBottom = new int[]{negativeBoder, 0, negativeBoder, negativeBoder};
                break;
            case DrawBorder.RIGHT:
                layerLeftTopRightBottom = new int[]{negativeBoder, negativeBoder, 0, negativeBoder};
                break;
            case DrawBorder.BOTTOM:
                layerLeftTopRightBottom = new int[]{negativeBoder, negativeBoder, negativeBoder, 0};
                break;
            case DrawBorder.LEFT_TOP:
                layerLeftTopRightBottom = new int[]{0, 0, negativeBoder, negativeBoder};
                break;
            case DrawBorder.LEFT_RIGHT:
                layerLeftTopRightBottom = new int[]{0, negativeBoder, 0, negativeBoder};
                break;
            case DrawBorder.LEFT_BOTTOM:
                layerLeftTopRightBottom = new int[]{0, negativeBoder, negativeBoder, 0};
                break;
            case DrawBorder.TOP_RIGHT:
                layerLeftTopRightBottom = new int[]{negativeBoder, 0, 0, negativeBoder};
                break;
            case DrawBorder.TOP_BOTTOM:
                layerLeftTopRightBottom = new int[]{negativeBoder, 0, negativeBoder, 0};
                break;
            case DrawBorder.RIGHT_BOTTOM:
                layerLeftTopRightBottom = new int[]{negativeBoder, negativeBoder, 0, 0};
                break;
            case DrawBorder.LEFT_TOP_RIGHT:
                layerLeftTopRightBottom = new int[]{0, 0, 0, negativeBoder};
                break;
            case DrawBorder.LEFT_TOP_BOTTOM:
                layerLeftTopRightBottom = new int[]{0, 0, negativeBoder, 0};
                break;
            case DrawBorder.LEFT_RIGHT_BOTTOM:
                layerLeftTopRightBottom = new int[]{0, negativeBoder, 0, 0};
                break;
            case DrawBorder.TOP_RIGHT_BOTTOM:
                layerLeftTopRightBottom = new int[]{negativeBoder, 0, 0, 0};
                break;
            case DrawBorder.ALL:
                layerLeftTopRightBottom = new int[]{0, 0, 0, 0};
                break;
            case DrawBorder.NONE:
            default:
                layerLeftTopRightBottom = new int[]{negativeBoder, negativeBoder, negativeBoder, negativeBoder};
                break;
        }
        return layerLeftTopRightBottom;
    }
}
