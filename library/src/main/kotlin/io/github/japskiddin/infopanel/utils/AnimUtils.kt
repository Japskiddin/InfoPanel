package io.github.japskiddin.infopanel.utils

import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation

private const val ANIMATION_DURATION: Long = 220L

internal val showTranslateAnimation: Animation
    get() = TranslateAnimation(
        Animation.RELATIVE_TO_SELF,
        0f,
        Animation.RELATIVE_TO_SELF,
        0f,
        Animation.RELATIVE_TO_SELF,
        1f,
        Animation.RELATIVE_TO_SELF,
        0f
    ).apply {
        duration = ANIMATION_DURATION
        interpolator = DecelerateInterpolator()
    }

internal val hideTranslateAnimation: Animation
    get() = TranslateAnimation(
        Animation.RELATIVE_TO_SELF,
        0f,
        Animation.RELATIVE_TO_SELF,
        0f,
        Animation.RELATIVE_TO_SELF,
        0f,
        Animation.RELATIVE_TO_SELF,
        1f
    ).apply {
        duration = ANIMATION_DURATION
        interpolator = AccelerateInterpolator()
    }
