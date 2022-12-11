package com.example.pokeapp.ui.animations

import android.animation.Animator
import android.app.Activity
import android.view.View
import com.example.pokeapp.R
import com.example.pokeapp.ui.animations.AnimatorUtil.concatAnimators
import com.example.pokeapp.utilities.ScreenDimensions

/**
 * This class provides a rebound animation for views.
 */
class ReboundAnimator @JvmOverloads constructor(
    context: Activity?, reboundDirection: ReboundAnimatorType = ReboundAnimatorType.RIGHT_TO_LEFT
) {
    private var context: Activity? = null
    private var translationAnimatorType: ViewAnimatorHelper.TranslationAnimatorType? = null
    /**
     * Constructor.
     *
     * @param context          Application context.
     * @param reboundDirection Indicates direction of rebound animation.
     */
    /**
     * Constructor.
     */
    init {
        initializeVariables(context)
        setTranslationAnimatorTypeForReboundDirection(reboundDirection)
    }

    /**
     * Initializes class level variables.
     *
     * @param context Application context.
     */
    private fun initializeVariables(context: Activity?) {
        this.context = context
    }

    /**
     * Determines which direction the rebound animator should be made.
     *
     * @param reboundDirection Direction for the rebound animation.
     */
    private fun setTranslationAnimatorTypeForReboundDirection(reboundDirection: ReboundAnimatorType) {
        if (ReboundAnimatorType.RIGHT_TO_LEFT == reboundDirection) {
            setTranslationAnimatorType(ViewAnimatorHelper.TranslationAnimatorType.TRANSLATION_X)
        } else {
            setTranslationAnimatorType(ViewAnimatorHelper.TranslationAnimatorType.TRANSLATION_Y)
        }
    }

    /**
     * Sets the animation translation direction to use.
     *
     * @param translationAnimatorType TranslationAnimatorType instance indicating translate
     * animation direction.
     */
    private fun setTranslationAnimatorType(translationAnimatorType: ViewAnimatorHelper.TranslationAnimatorType) {
        this.translationAnimatorType = translationAnimatorType
    }/*- If translation animation type is "TRANSLATION_X", we need to use the width of the
        screen, otherwise, use the height. */

    /**
     * Determines the fromValue to use for the translate animation.
     *
     * @return Value to use for the translate animation fromValue property.
     */
    private val translationFromValue: Float
        get() {

            /*- If translation animation type is "TRANSLATION_X", we need to use the width of the
        screen, otherwise, use the height. */
            val translationFromValue: Float =
                if (translationAnimatorType == ViewAnimatorHelper.TranslationAnimatorType.TRANSLATION_X) {
                    ScreenDimensions.getWidthPixels(context).toFloat()
                } else {
                    ScreenDimensions.getHeightPixels(context).toFloat()
                }
            return translationFromValue
        }

    /**
     * Creates a rebound animation for a view.
     *
     * @param viewToAnimate View to animate.
     * @return Animator array containing the animations to play for a rebound
     * animation.
     */
    fun getReboundAnimatorForView(viewToAnimate: View?): Array<Animator?> {
        // Get translation animator.
        val translateAnimator = ViewAnimatorHelper.getTranslationAnimatorForViews(
            translationAnimatorType!!, translationFromValue, 0.0f, 0, 0, viewToAnimate
        )
        translateAnimator[0]!!.addListener(
            MyAnimatorListener(
                viewToAnimate, ReboundAnimationToPlay.FIRST_REBOUND
            )
        )

        // Get fade animator.
        val fadeAnimator = ViewAnimatorHelper.getFadeAnimatorForViews(
            0, 0, ViewAnimatorHelper.FadeAnimatorType.FADE_IN, viewToAnimate
        )

        // Concatenate translation and fade animators into a single array.
        return concatAnimators(translateAnimator, fadeAnimator)
    }

    /**
     * Defines the animation state.
     */
    private enum class ReboundAnimationToPlay {
        FIRST_REBOUND, SECOND_REBOUND
    }

    /**
     * Defines the animation to play.
     */
    enum class ReboundAnimatorType {
        RIGHT_TO_LEFT, BOTTOM_TO_TOP
    }

    /**
     * This AnimatorListener is used to perform a rebound animation.
     */
    private inner class MyAnimatorListener(// The view to animate.
        private val viewToAnimate: View?, // Rebound animation to play.
        private val reboundAnimationToPlay: ReboundAnimationToPlay
    ) : Animator.AnimatorListener {
        override fun onAnimationCancel(arg0: Animator) {}
        override fun onAnimationEnd(arg0: Animator) {
            when (reboundAnimationToPlay) {
                ReboundAnimationToPlay.FIRST_REBOUND -> {
                    val translateAnimator = ViewAnimatorHelper.getTranslationAnimator(
                        translationAnimatorType!!,
                        null,
                        context!!.resources.getDimensionPixelSize(R.dimen.rebound_distance)
                            .toFloat(),
                        viewToAnimate
                    )
                    translateAnimator.addListener(
                        MyAnimatorListener(
                            viewToAnimate, ReboundAnimationToPlay.SECOND_REBOUND
                        )
                    )
                    translateAnimator.start()
                }
                ReboundAnimationToPlay.SECOND_REBOUND -> {
                    val translateAnimator = ViewAnimatorHelper.getTranslationAnimator(
                        translationAnimatorType!!, null, 0.0f, viewToAnimate
                    )
                    translateAnimator.start()
                }
            }
        }

        override fun onAnimationRepeat(arg0: Animator) {}
        override fun onAnimationStart(arg0: Animator) {}
    }
}