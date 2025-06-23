package io.github.japskiddin.infopanel.utils

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import androidx.core.graphics.toColorInt

private const val RIPPLE_COLOR: String = "#66FFFFFF"
private const val RIPPLE_MASK_COLOR: String = "#FFFFFF"
private const val RIPPLE_CORNER_RADIUS: Float = 6f

internal fun createRippleEffect(): RippleDrawable {
    val rippleColor = ColorStateList.valueOf(RIPPLE_COLOR.toColorInt())
    val mask = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = RIPPLE_CORNER_RADIUS.toDp()
        color = ColorStateList.valueOf(RIPPLE_MASK_COLOR.toColorInt())
    }
    return RippleDrawable(rippleColor, null, mask)
}

private fun Float.toDp(): Float = this * density

private val density: Float
    get() = systemResources.displayMetrics.density

private val systemResources: Resources
    get() = Resources.getSystem()
