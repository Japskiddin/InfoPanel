package io.github.japskiddin.infopanel.utils

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import java.util.*

private const val RIPPLE_COLOR: String = "#66FFFFFF"
private const val RIPPLE_MASK_COLOR: String = "#FFFFFF"
private const val RIPPLE_CORNER_RADIUS: Float = 6f

internal enum class InsetsPadding {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM,
}

internal fun View.applyWindowInsetsMargin(mask: Int, paddings: EnumSet<InsetsPadding>) {
    val initialParams = layoutParams as MarginLayoutParams
    val initialLeft = initialParams.leftMargin
    val initialTop = initialParams.topMargin
    val initialRight = initialParams.rightMargin
    val initialBottom = initialParams.bottomMargin
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, windowInsets ->
        val insets = windowInsets.getInsets(mask)
        val params = (v.layoutParams as MarginLayoutParams).apply {
            leftMargin = if (paddings.contains(InsetsPadding.LEFT)) {
                insets.left + initialLeft
            } else {
                initialLeft
            }
            topMargin = if (paddings.contains(InsetsPadding.TOP)) {
                insets.top + initialTop
            } else {
                initialTop
            }
            rightMargin = if (paddings.contains(InsetsPadding.RIGHT)) {
                insets.right + initialRight
            } else {
                initialRight
            }
            bottomMargin = if (paddings.contains(InsetsPadding.BOTTOM)) {
                insets.bottom + initialBottom
            } else {
                initialBottom
            }
        }
        v.layoutParams = params
        windowInsets
    }
}

internal fun View.applyWindowInsetsPadding(mask: Int, paddings: EnumSet<InsetsPadding>) {
    val initialLeft = paddingLeft
    val initialTop = paddingTop
    val initialRight = paddingRight
    val initialBottom = paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, windowInsets ->
        val insets = windowInsets.getInsets(mask)
        v.updatePadding(
            left = if (paddings.contains(InsetsPadding.LEFT)) {
                insets.left + initialLeft
            } else {
                initialLeft
            },
            top = if (paddings.contains(InsetsPadding.TOP)) {
                insets.top + initialTop
            } else {
                initialTop
            },
            right = if (paddings.contains(InsetsPadding.RIGHT)) {
                insets.right + initialRight
            } else {
                initialRight
            },
            bottom = if (paddings.contains(InsetsPadding.BOTTOM)) {
                insets.bottom + initialBottom
            } else {
                initialBottom
            }
        )
        windowInsets
    }
}

internal fun createRippleEffect(): RippleDrawable {
    val rippleColor = ColorStateList.valueOf(RIPPLE_COLOR.toColorInt())
    val mask = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = RIPPLE_CORNER_RADIUS.toDp()
        color = ColorStateList.valueOf(RIPPLE_MASK_COLOR.toColorInt())
    }
    return RippleDrawable(rippleColor, null, mask)
}

internal fun View?.findSuitableParent(): ViewGroup {
    var current = this
    do {
        if (current is FrameLayout || current is CoordinatorLayout) {
            return current
        }
        val parent = current?.parent
        current = parent as? View
    } while (current != null)
    throw IllegalArgumentException("No suitable parent found for InfoPanel, should be FrameLayout or CoordinatorLayout")
}

private fun Float.toDp(): Float = this * density

private val density: Float
    get() = systemResources.displayMetrics.density

private val systemResources: Resources
    get() = Resources.getSystem()
