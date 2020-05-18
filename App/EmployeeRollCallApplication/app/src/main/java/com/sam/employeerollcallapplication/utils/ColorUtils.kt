package com.sam.employeerollcallapplication.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.ColorInt

class ColorUtil {
    companion object {
        @ColorInt
        fun getColorFromStyle(context: Context, colorResId: Int): Int {
            val typedArray =
                context.obtainStyledAttributes(TypedValue().data, intArrayOf(colorResId))
            val color = typedArray.getColor(0, 0)
            typedArray.recycle()
            return color
        }
    }
}