package com.example.kyler.customedittext;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.EditText;

/**
 * Created by kyler on 2/10/17.
 */
public class CustomDrawable {


    public static void setKylerDrawable(EditText edt) {
        edt.setBackground(getLayerDrawableBackground(edt, DrawBorder.BOTTOM, R.color.colorAccent, DrawBorder.TOP, R.color.error));
    }

    public static LayerDrawable getLayerDrawableBackground(EditText editText, int flagLayer1, int layer1Color, int flagLayer2, int layer2Color) {
        int[] layer1Inset = getLayerInset(flagLayer1, 10);

        GradientDrawable underLayer = new GradientDrawable();
        underLayer.setCornerRadius(10);
        underLayer.setStroke(10, editText.getResources().getColor(layer1Color));

        int[] layer2Inset = getLayerInset(flagLayer2, 10);

        GradientDrawable surfaceLayer = new GradientDrawable();
        surfaceLayer.setStroke(10, editText.getResources().getColor(layer2Color));

        Drawable[] layers = {underLayer, surfaceLayer};
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        layerDrawable.setLayerInset(0, layer1Inset[0], layer1Inset[1], layer1Inset[2], layer1Inset[3]);
        layerDrawable.setLayerInset(1, layer2Inset[0], layer2Inset[1], layer2Inset[2], layer2Inset[3]);

        return layerDrawable;
    }

    private static int[] getLayerInset(int flag, int borderWith) {
        int negativeBoder = borderWith * -1;
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
