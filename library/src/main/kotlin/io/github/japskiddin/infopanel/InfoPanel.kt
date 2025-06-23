package io.github.japskiddin.infopanel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import io.github.japskiddin.infopanel.databinding.InfoPanelBinding
import io.github.japskiddin.infopanel.utils.createRippleEffect
import io.github.japskiddin.infopanel.utils.hideTranslateAnimation
import io.github.japskiddin.infopanel.utils.showTranslateAnimation

public class InfoPanel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = InfoPanelBinding.inflate(LayoutInflater.from(context), this, true)
    private val showAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            showInternal()
        }

        override fun onAnimationEnd(animation: Animation) = Unit

        override fun onAnimationRepeat(animation: Animation) = Unit
    }
    private val hideAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) = Unit

        override fun onAnimationEnd(animation: Animation) {
            hideInternal()
        }

        override fun onAnimationRepeat(animation: Animation) = Unit
    }
    private val showAnimation = showTranslateAnimation.apply {
        setAnimationListener(showAnimationListener)
    }
    private val hideAnimation = hideTranslateAnimation.apply {
        setAnimationListener(hideAnimationListener)
    }
    private val handler = Handler(Looper.getMainLooper())
    private val hideRunnable = Runnable { hide() }

    public val isNotEmpty: Boolean
        get() = binding.tvDescription.text.isNotEmpty()

    private val isVisibleOrInvisible: Boolean
        get() = isVisible || isInvisible

    private val isGoneOrInvisible: Boolean
        get() = isGone || isInvisible

    init {
        val ripple = createRippleEffect()
        binding.tvAction.foreground = ripple
    }

    @JvmOverloads
    public fun make(
        @StringRes textRes: Int,
        duration: Long,
        @StringRes actionRes: Int? = null,
        listener: OnClickListener? = null
    ) {
        val text = context.getString(textRes)
        val action = if (actionRes != null) {
            context.getString(actionRes)
        } else {
            ""
        }
        make(text, duration, action, listener)
    }

    @JvmOverloads
    public fun make(
        text: String,
        duration: Long,
        action: String,
        listener: OnClickListener? = null
    ) {
        hide(false)
        clear()
        setDescription(text)
        setAction(action, listener)
        show()
        handler.removeCallbacks(hideRunnable)
        if (duration > 0L) {
            handler.postDelayed(hideRunnable, duration)
        }
    }

    public fun dismiss() {
        hide()
    }

    @JvmOverloads
    public fun show(withAnimation: Boolean = true) {
        if (isVisibleOrInvisible) return
        if (withAnimation) {
            clearAnimation()
            visibility = INVISIBLE
            startAnimation(showAnimation)
        } else {
            showInternal()
        }
    }

    @JvmOverloads
    public fun hide(withAnimation: Boolean = true) {
        if (isGoneOrInvisible) return
        if (withAnimation) {
            clearAnimation()
            startAnimation(hideAnimation)
        } else {
            hideInternal()
        }
    }

    private fun setDescription(text: String) {
        binding.tvDescription.text = text
    }

    private fun setAction(action: String, listener: OnClickListener?) {
        if (action.isEmpty()) return
        with(binding.tvAction) {
            visibility = VISIBLE
            text = action
            setOnClickListener(listener)
        }
    }

    private fun clear() {
        setDescription("")
        clearAction()
        handler.removeCallbacks(hideRunnable)
    }

    private fun clearAction() {
        with(binding.tvAction) {
            text = ""
            visibility = GONE
            setOnClickListener(null)
        }
    }

    private fun showInternal() {
        visibility = VISIBLE
    }

    private fun hideInternal() {
        visibility = GONE
        clear()
    }

    public companion object {
        public const val LENGTH_LONG: Long = 5000L
        public const val LENGTH_SHORT: Long = 3000L
        public const val LENGTH_INDEFINITE: Long = -1L
    }
}
