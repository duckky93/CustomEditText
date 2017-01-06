/*
+--------------------------------------------------------------------------
|
| WARNING: REMOVING THIS COPYRIGHT HEADER IS EXPRESSLY FORBIDDEN
|
| FIX QUICKER APPLICATION
| ========================================
| by ENCLAVE, INC.
|  2015-2016 ENCLAVEIT.COM - All right reserved
| Website: http://www.enclaveit.com [^]
| Email : engineering@enclave.vn
| ========================================
|
| WARNING //--------------------------
|
| Selling the code for this program without prior written consent is expressly
|forbidden.
| This computer program is protected by copyright law.
| Unauthorized reproduction or distribution of this program, or any portion of
| if, may result in severe civil and criminal penalties and will be prosecuted
|to the maximum extent possible under the law.
+--------------------------------------------------------------------------
*/
package com.example.kyler.customedittext;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by kyler on 1/6/17.
 */
public class Utils {

    public static int dpToPx(Resources resources, int dp) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Resources resources, int px) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
