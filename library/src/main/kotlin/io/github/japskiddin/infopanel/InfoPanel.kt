package io.github.japskiddin.infopanel

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.WindowInsetsCompat
import io.github.japskiddin.infopanel.utils.InsetsPadding
import io.github.japskiddin.infopanel.utils.applyWindowInsetsPadding
import io.github.japskiddin.infopanel.utils.createRippleEffect
import io.github.japskiddin.infopanel.utils.findSuitableParent
import io.github.japskiddin.infopanel.utils.hideTranslateAnimation
import io.github.japskiddin.infopanel.utils.showTranslateAnimation
import java.util.*

public class InfoPanel private constructor(
    private val parent: ViewGroup,
    private val message: String,
    private val duration: Long,
) {
    public enum class AnimationType {
        SLIDE,
        NONE,
    }

    private val rootView: View = LayoutInflater.from(parent.context).inflate(R.layout.info_panel, parent, false)

    private var actionText: String = ""
    private var actionCallback: (() -> Unit)? = null
    private var animationType: AnimationType = AnimationType.SLIDE

    private val showAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
        }

        override fun onAnimationEnd(animation: Animation) = Unit

        override fun onAnimationRepeat(animation: Animation) = Unit
    }
    private val hideAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) = Unit

        override fun onAnimationEnd(animation: Animation) {
        }

        override fun onAnimationRepeat(animation: Animation) = Unit
    }
    private val showAnimation = showTranslateAnimation.apply {
        setAnimationListener(showAnimationListener)
    }
    private val hideAnimation = hideTranslateAnimation.apply {
        setAnimationListener(hideAnimationListener)
    }

    public fun setAction(text: String, callback: () -> Unit): InfoPanel = apply {
        this.actionText = text
        this.actionCallback = callback
    }

    public fun setAnimationType(type: AnimationType): InfoPanel = apply {
        this.animationType = type
    }

    public fun show() {
        val tvMessage = rootView.findViewById<TextView>(R.id.info_panel_message)
        val tvAction = rootView.findViewById<TextView>(R.id.info_panel_action)

        tvMessage.text = message

        if (actionText.isNotEmpty()) {
            val ripple = createRippleEffect()
            tvAction.foreground = ripple
            tvAction.text = actionText
            tvAction.visibility = VISIBLE
            tvAction.setOnClickListener {
                actionCallback?.invoke()
                dismiss()
            }
        }

        rootView.applyWindowInsetsPadding(
            mask = WindowInsetsCompat.Type.displayCutout()
                or WindowInsetsCompat.Type.systemBars()
                or WindowInsetsCompat.Type.ime(),
            paddings = EnumSet.of(InsetsPadding.BOTTOM, InsetsPadding.LEFT, InsetsPadding.RIGHT),
        )

        if (rootView.parent == null) {
            parent.addView(rootView)
        }

        rootView.visibility = INVISIBLE

        rootView.post {
            rootView.translationY = rootView.height.toFloat()

            rootView.animate()
                .translationY(0f)
                .setDuration(220L)
                .withStartAction {
                    rootView.visibility = VISIBLE
                }
                .start()

            if (duration > 0L) {
                rootView.postDelayed({ dismiss() }, duration)
            }
        }
    }

    public fun dismiss() {
        rootView.animate()
            .translationY(rootView.height.toFloat())
            .setDuration(220L)
            .withEndAction {
                parent.removeView(rootView)
            }
            .start()
    }

    public companion object {
        public const val DURATION_LONG: Long = 5000L
        public const val DURATION_SHORT: Long = 3000L
        public const val DURATION_INDEFINITE: Long = -1L

        @JvmOverloads
        @JvmStatic
        public fun make(
            view: View,
            @StringRes messageRes: Int,
            duration: Long = DURATION_SHORT,
        ): InfoPanel {
            val context = view.context
            val message = context.getString(messageRes)
            return makeInternal(view, message, duration)
        }

        @JvmOverloads
        @JvmStatic
        public fun make(
            view: View,
            message: String,
            duration: Long = DURATION_SHORT,
        ): InfoPanel = makeInternal(view, message, duration)

        private fun makeInternal(
            view: View,
            message: String,
            duration: Long = DURATION_SHORT,
        ): InfoPanel {
            val parent = view.findSuitableParent()
            return InfoPanel(parent, message, duration)
        }
    }
}
