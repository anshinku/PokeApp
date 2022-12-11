package com.example.pokeapp.utilities;

import android.content.Context;


/**
 * This class provides utilities methods to get screen dimensions.
 */
public class ScreenDimensions {
    /**
     * Determines screen width in pixels.
     *
     * @param context Application context.
     * @return Screen width in pixels.
     */
    public static int getWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Determines screen height in pixels.
     *
     * @param context Application context.
     * @return Screen height in pixels.
     */
    public static int getHeightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * Determines the density of the display.
     *
     * @param context Application context.
     * @return Density of the display.
     */
    public static float getDisplayDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * Indicates if the screen has at least 240px in the Y axis.
     *
     * @param context Application context.
     * @return True if the screen has at least 240px in the Y axis, false otherwise.
     */
    public static boolean screenHasAtLeast240pxInYAxis(Context context) {
        return context.getResources().getDisplayMetrics().ydpi >= 240;
    }
}