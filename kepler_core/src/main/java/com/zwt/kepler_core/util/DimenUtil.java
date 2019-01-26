package com.zwt.kepler_core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.zwt.kepler_core.application.Kepler;

/**
 * @author ZWT
 */
public class DimenUtil {

    public static  int getScreenWidth(){
        Resources resources = Kepler.getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(){
        Resources resources = Kepler.getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.heightPixels;
    }
}
