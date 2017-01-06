package com.example.kyler.customedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DuckKy on 1/5/2017.
 */

public class CustomEditText extends LinearLayout {

    @BindView(R.id.text_view_message)
    TextView tvMessage;

    @BindView(R.id.edit_text_main)
    EditText edMain;

    private final int NONE = 0;
    private final int LEFT = 1;
    private final int TOP = 2;
    private final int RIGHT = 4;
    private final int BOTTOM = 8;
    private final int LEFT_TOP = 3;
    private final int LEFT_RIGHT = 5;
    private final int LEFT_BOTTOM = 9;
    private final int TOP_RIGHT = 6;
    private final int TOP_BOTTOM = 10;
    private final int RIGHT_BOTTOM = 12;
    private final int LEFT_TOP_RIGHT = 7;
    private final int LEFT_TOP_BOTTOM = 11;
    private final int LEFT_RIGHT_BOTTOM = 13;
    private final int TOP_RIGHT_BOTTOM = 14;
    private final int ALL = 15;

    private final int CORNER_NONE = 0;
    private final int CORNER_LEFT_TOP = 1;
    private final int CORNER_TOP_RIGHT = 2;
    private final int CORNER_RIGHT_BOTTOM = 4;
    private final int CORNER_BOTTOM_LEFT = 8;
    private final int CORNER_LEFT_TOP_TOP_RIGHT = 3;
    private final int CORNER_LEFT_TOP_BOTTOM_LEFT = 9;
    private final int CORNER_RIGHT_BOTTOM_BOTTOM_LEFT = 12;
    private final int CORNER_TOP_RIGHT_RIGHT_BOTTOM = 6;
    private final int CORNER_LEFT_TOP_TOP_RIGHT_RIGHT_BOTTOM = 7;
    private final int CORNER_BOTTOM_LEFT_LEFT_TOP_TOP_RIGHT = 11;
    private final int CORNER_RIGHT_BOTTOM_BOTTOM_LEFT_LEFT_TOP = 13;
    private final int CORNER_TOP_RIGHT_RIGHT_BOTTOM_BOTTOM_LEFT = 14;
    private final int CORNER_LEFT_TOP_RIGHT_BOTTOM = 5;
    private final int CORNER_BOTTOM_LEFT_TOP_RIGHT = 10;
    private final int CORNER_ALL = 15;

    private final int COLOR_TRANSPARENT_DEFAULT = getResources().getColor(R.color.transparent);
    private final int COLOR_WHITE_DEFAULT = getResources().getColor(R.color.white);

    private int drawBorderFlag = NONE;
    private int drawCornerFlag = ALL;
    private int borderWith = 0;
    private int radius = 0;
    private int borderColorNormal = COLOR_TRANSPARENT_DEFAULT;
    private int borderColorForcus = COLOR_TRANSPARENT_DEFAULT;
    private int backgroundColor = COLOR_WHITE_DEFAULT;

    public CustomEditText(Context context) {
        this(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_custom_edit_text, this, true);
        ButterKnife.bind(this, view);
        TypedArray typeProperty = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomEditText,
                0, 0);
        try {
            borderWith = (int) typeProperty.getDimension(R.styleable.CustomEditText_borderWidth, 0f);
            radius = (int) typeProperty.getDimension(R.styleable.CustomEditText_radius, 0f);
            drawBorderFlag = typeProperty.getInt(R.styleable.CustomEditText_drawBorder, NONE);
            drawCornerFlag = typeProperty.getInt(R.styleable.CustomEditText_drawCorner, ALL);
            borderColorNormal = typeProperty.getColor(R.styleable.CustomEditText_borderColorNormal, COLOR_TRANSPARENT_DEFAULT);
            borderColorForcus = typeProperty.getColor(R.styleable.CustomEditText_borderColorFocus, COLOR_TRANSPARENT_DEFAULT);
            backgroundColor = typeProperty.getColor(R.styleable.CustomEditText_backgroundColor, COLOR_WHITE_DEFAULT);
        } finally {
            typeProperty.recycle();
        }
        edMain.setPadding((int) (borderWith * 1.5), (int) (borderWith * 1.5), (int) (borderWith * 1.5), (int) (borderWith * 1.5));
        edMain.setBackground(getEditTextBackground(drawBorderFlag, drawCornerFlag, radius));
    }

    private LayerDrawable getEditTextBackground(int drawBorderFlag, int drawCornerFlag, float radius) {
        initCorner(drawCornerFlag, radius);
        RoundRectShape underLayerRoundRectShape = new RoundRectShape(underCorner, null, null);
        ShapeDrawable underLayer = new ShapeDrawable(underLayerRoundRectShape);
        underLayer.getPaint().setColor(borderColorForcus);

        RoundRectShape surfaceLayerRoundRectShape = new RoundRectShape(surfaceCorner, null, null);
        ShapeDrawable surfaceLayer = new ShapeDrawable(surfaceLayerRoundRectShape);
        surfaceLayer.getPaint().setColor(backgroundColor);

        Drawable[] layers = {underLayer, surfaceLayer};
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        int[] layerLeftTopRightBottom = getLayerInset(drawBorderFlag);
        layerDrawable.setLayerInset(0, 0, 0, 0, 0);
        layerDrawable.setLayerInset(1, layerLeftTopRightBottom[0], layerLeftTopRightBottom[1], layerLeftTopRightBottom[2], layerLeftTopRightBottom[3]);

        return layerDrawable;
    }

    private float[] underCorner, surfaceCorner;

    private void initCorner(int flag, float radius) {
        float minus = radius - borderWith;
        float surfaceRadius = (minus < (radius / 2)) ? (radius / 2) : minus;
        switch (flag) {
            case CORNER_NONE:
                surfaceCorner = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
                underCorner = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
                break;
            case CORNER_LEFT_TOP:
                underCorner = new float[]{radius, radius, 0, 0, 0, 0, 0, 0};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, 0, 0, 0, 0, 0, 0};
                break;
            case CORNER_BOTTOM_LEFT:
                underCorner = new float[]{0, 0, 0, 0, 0, 0, radius, radius};
                surfaceCorner = new float[]{0, 0, 0, 0, 0, 0, surfaceRadius, surfaceRadius};
                break;
            case CORNER_TOP_RIGHT:
                underCorner = new float[]{0, 0, radius, radius, 0, 0, 0, 0};
                surfaceCorner = new float[]{0, 0, surfaceRadius, surfaceRadius, 0, 0, 0, 0};
                break;
            case CORNER_RIGHT_BOTTOM:
                underCorner = new float[]{0, 0, 0, 0, radius, radius, 0, 0};
                surfaceCorner = new float[]{0, 0, 0, 0, surfaceRadius, surfaceRadius, 0, 0};
                break;
            case CORNER_LEFT_TOP_TOP_RIGHT:
                underCorner = new float[]{radius, radius, radius, radius, 0, 0, 0, 0};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, 0, 0, 0, 0};
                break;
            case CORNER_LEFT_TOP_BOTTOM_LEFT:
                underCorner = new float[]{radius, radius, 0, 0, 0, 0, radius, radius};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, 0, 0, 0, 0, surfaceRadius, surfaceRadius};
                break;
            case CORNER_RIGHT_BOTTOM_BOTTOM_LEFT:
                underCorner = new float[]{0, 0, 0, 0, radius, radius, radius, radius};
                surfaceCorner = new float[]{0, 0, 0, 0, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius};
                break;
            case CORNER_TOP_RIGHT_RIGHT_BOTTOM:
                underCorner = new float[]{0, 0, radius, radius, radius, radius, 0, 0};
                surfaceCorner = new float[]{0, 0, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, 0, 0};
                break;
            case CORNER_LEFT_TOP_TOP_RIGHT_RIGHT_BOTTOM:
                underCorner = new float[]{radius, radius, radius, radius, radius, radius, 0, 0};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, 0, 0};
                break;
            case CORNER_BOTTOM_LEFT_LEFT_TOP_TOP_RIGHT:
                underCorner = new float[]{radius, radius, radius, radius, 0, 0, radius, radius};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, 0, 0, surfaceRadius, surfaceRadius};
                break;
            case CORNER_RIGHT_BOTTOM_BOTTOM_LEFT_LEFT_TOP:
                underCorner = new float[]{radius, radius, 0, 0, radius, radius, radius, radius};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, 0, 0, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius};
                break;
            case CORNER_TOP_RIGHT_RIGHT_BOTTOM_BOTTOM_LEFT:
                underCorner = new float[]{0, 0, radius, radius, radius, radius, radius, radius};
                surfaceCorner = new float[]{0, 0, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius, surfaceRadius};
                break;
            case CORNER_LEFT_TOP_RIGHT_BOTTOM:
                underCorner = new float[]{radius, radius, 0, 0, radius, radius, 0, 0};
                surfaceCorner = new float[]{surfaceRadius, surfaceRadius, 0, 0, surfaceRadius, surfaceRadius, 0, 0};
                break;
            case CORNER_BOTTOM_LEFT_TOP_RIGHT:
                underCorner = new float[]{0, 0, radius, radius, 0, 0, radius, radius};
                surfaceCorner = new float[]{0, 0, surfaceRadius, surfaceRadius, 0, 0, surfaceRadius, surfaceRadius};
                break;
            case CORNER_ALL:
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
            case LEFT:
                layerLeftTopRightBottom = new int[]{borderWith, 0, 0, 0};
                break;
            case TOP:
                layerLeftTopRightBottom = new int[]{0, borderWith, 0, 0};
                break;
            case RIGHT:
                layerLeftTopRightBottom = new int[]{0, 0, borderWith, 0};
                break;
            case BOTTOM:
                layerLeftTopRightBottom = new int[]{0, 0, 0, borderWith};
                break;
            case LEFT_TOP:
                layerLeftTopRightBottom = new int[]{borderWith, borderWith, 0, 0};
                break;
            case LEFT_RIGHT:
                layerLeftTopRightBottom = new int[]{borderWith, 0, borderWith, 0};
                break;
            case LEFT_BOTTOM:
                layerLeftTopRightBottom = new int[]{borderWith, 0, 0, borderWith};
                break;
            case TOP_RIGHT:
                layerLeftTopRightBottom = new int[]{0, borderWith, borderWith, 0};
                break;
            case TOP_BOTTOM:
                layerLeftTopRightBottom = new int[]{0, borderWith, 0, borderWith};
                break;
            case RIGHT_BOTTOM:
                layerLeftTopRightBottom = new int[]{0, 0, borderWith, borderWith};
                break;
            case LEFT_TOP_RIGHT:
                layerLeftTopRightBottom = new int[]{borderWith, borderWith, borderWith, 0};
                break;
            case LEFT_TOP_BOTTOM:
                layerLeftTopRightBottom = new int[]{borderWith, borderWith, 0, borderWith};
                break;
            case LEFT_RIGHT_BOTTOM:
                layerLeftTopRightBottom = new int[]{borderWith, 0, borderWith, borderWith};
                break;
            case TOP_RIGHT_BOTTOM:
                layerLeftTopRightBottom = new int[]{0, borderWith, borderWith, borderWith};
                break;
            case ALL:
                layerLeftTopRightBottom = new int[]{borderWith, borderWith, borderWith, borderWith};
                break;
            case NONE:
            default:
                layerLeftTopRightBottom = new int[]{0, 0, 0, 0};
                break;
        }
        return layerLeftTopRightBottom;
    }
}
