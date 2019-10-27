package com.example.simpleLanguage.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics

/**
 * This class is used for getting all the resources to be used in the app.
 * Initialize only in Application class with Application context to avoid memory leaks.
 */
@SuppressLint("StaticFieldLeak")
object ResourceUtils {

    private var mContext: Context? = null

    /**
     * @return DisplayMetrics
     */
    val displayMetrics: DisplayMetrics
        get() = mContext!!.resources.displayMetrics

    /**
     * @return Float screen height
     */
    fun getScreenHeight() = displayMetrics.heightPixels

    /**
     * @return Float screen width
     */
    fun getScreenwidth() = displayMetrics.widthPixels

    /**
     * @return true if landscape mode else false
     */
    val isLandscapeMode: Boolean
        get() = mContext!!.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun initialize(context: Context) {
        mContext = context
    }

    /**
     * @param id the integer id for the string
     * @return the string value
     */
    fun getString(id: Int): String {
        return mContext!!.resources.getString(id)
    }

    /**
     * @param id the integer id for the drawable
     * @return drawable
     */
    fun getDrawable(id: Int): Drawable {
        return mContext!!.resources.getDrawable(id)
    }

    fun getString(id: Int, number: Int): String {
        return mContext!!.resources.getString(id, number)
    }

    fun getString(id: Int, vararg formatArgs: Any): String {
        return mContext!!.resources.getString(id, *formatArgs)
    }

    /**
     * @param color the integer id for the color
     * @return color
     */
    fun getColor(color: Int): Int {
        return mContext!!.resources.getColor(color)
    }


}