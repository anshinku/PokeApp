package com.example.pokeapp.utilities

import android.content.Context

/**
 * This class provides utilities methods to get screen dimensions.
 */
object ScreenDimensions {
    /**
     * Determines screen width in pixels.
     *
     * @param context Application context.
     * @return Screen width in pixels.
     */
    fun getWidthPixels(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * Determines screen height in pixels.
     *
     * @param context Application context.
     * @return Screen height in pixels.
     */
    fun getHeightPixels(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * Determines the density of the display.
     *
     * @param context Application context.
     * @return Density of the display.
     */
    fun getDisplayDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    /**
     * Indicates if the screen has at least 240px in the Y axis.
     *
     * @param context Application context.
     * @return True if the screen has at least 240px in the Y axis, false otherwise.
     */
    fun screenHasAtLeast240pxInYAxis(context: Context): Boolean {
        return context.resources.displayMetrics.ydpi >= 240
    }
}