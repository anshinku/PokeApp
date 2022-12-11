package com.example.pokeapp.ui.animations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

/**
 * This is a helper class that provides methods for view animations.
 */
object ViewAnimatorHelper {

    /**
     * Creates an array defining a translate animation for each view.
     *
     * @param translationAnimatorType         TranslationAnimatorType instance.
     * @param translationFromValue            Indicates from where the view should be translated.
     * @param translationToValue              Indicates where the view should be translated.
     * @param animatorDuration                Indicates the duration of the animation.
     * @param startDelayBetweenViewsAnimators Indicates the start delay between each animation.
     * @param viewsToAnimate                  Views to animate.
     * @return Array containing all the animators to play for given views.
     */
    fun getTranslationAnimatorForViews(
        translationAnimatorType: TranslationAnimatorType,
        translationFromValue: Float?,
        translationToValue: Float?,
        animatorDuration: Int,
        startDelayBetweenViewsAnimators: Int,
        vararg viewsToAnimate: View?
    ): Array<Animator?> {
        // Create array to allocate all animators.
        val allAnimators = arrayOfNulls<Animator>(viewsToAnimate.size)
        var viewToAnimate: View?
        var translateAnimator: ObjectAnimator
        var duration: Int
        for (index in viewsToAnimate.indices) {
            viewToAnimate = viewsToAnimate[index]

            // Determine animation duration for each view.
            duration = (animatorDuration + startDelayBetweenViewsAnimators * index)

            // Request a translate animator for each view.
            translateAnimator = getTranslationAnimator(
                translationAnimatorType, translationFromValue, translationToValue, viewToAnimate
            )
            translateAnimator.duration = duration.toLong()

            // Add each translate animator to the array.
            allAnimators[index] = translateAnimator
        }
        return allAnimators
    }

    /**
     * Creates a translation animator for a view.
     *
     * @param translationAnimatorType TranslationAnimatorType instance.
     * @param translationFromValue    Indicates from where the view should be translated.
     * @param translationToValue      Indicates where the view should be translated.
     * @param viewToAnimate           View to animate.
     * @return ObjectAnimator instance configured for the translation animation.
     */
    internal fun getTranslationAnimator(
        translationAnimatorType: TranslationAnimatorType,
        translationFromValue: Float?,
        translationToValue: Float?,
        viewToAnimate: View?
    ): ObjectAnimator {
        val translateAnimator: ObjectAnimator =
            if (translationAnimatorType == TranslationAnimatorType.TRANSLATION_X) {
                if (translationFromValue != null) {
                    ObjectAnimator.ofFloat(
                        viewToAnimate,
                        AnimationConstants.ViewProperties.TRANSLATION_X,
                        translationFromValue,
                        translationToValue!!
                    )
                } else {
                    ObjectAnimator.ofFloat(
                        viewToAnimate,
                        AnimationConstants.ViewProperties.TRANSLATION_X,
                        translationToValue!!
                    )
                }
            } else {
                if (translationFromValue != null) {
                    ObjectAnimator.ofFloat(
                        viewToAnimate,
                        AnimationConstants.ViewProperties.TRANSLATION_Y,
                        translationFromValue,
                        translationToValue!!
                    )
                } else {
                    ObjectAnimator.ofFloat(
                        viewToAnimate,
                        AnimationConstants.ViewProperties.TRANSLATION_Y,
                        translationToValue!!
                    )
                }
            }
        return translateAnimator
    }

    /**
     * Creates a fade animation for given views.
     *
     * @param startDelayBetweenViewsAnimators Indicates the start delay between each animation.
     * @param animatorDuration                Indicates the duration of the animation.
     * @param fadeAnimatorType                FadeAnimatorType instance.
     * @param viewsToAnimate                  Views to animate.
     * @return Array containing all the animators to play for given views.
     */
    fun getFadeAnimatorForViews(
        startDelayBetweenViewsAnimators: Int,
        animatorDuration: Int,
        fadeAnimatorType: FadeAnimatorType,
        vararg viewsToAnimate: View?
    ): Array<Animator?> {
        // Create array to allocate all animators.
        val allAnimators = arrayOfNulls<Animator>(viewsToAnimate.size)
        var viewToAnimate: View?
        var duration: Int
        for (index in viewsToAnimate.indices) {
            viewToAnimate = viewsToAnimate[index]

            // Determine animation duration for each view.
            duration = (animatorDuration + startDelayBetweenViewsAnimators * index)

            /*- Request a translate animator for each view and add each translate animator to
            the array. */allAnimators[index] = getFadeAnimator(
                fadeAnimatorType, viewToAnimate, duration
            )
        }
        return allAnimators
    }

    /**
     * Creates a fade animator for a view.
     *
     * @param fadeAnimatorType FadeAnimatorType instance.
     * @param viewToAnimate    View to animate.
     * @param animatorDuration Indicates the duration of the fade animation.
     * @return ObjectAnimator instance configured for the fade animation.
     */
    private fun getFadeAnimator(
        fadeAnimatorType: FadeAnimatorType, viewToAnimate: View?, animatorDuration: Int
    ): ObjectAnimator {
        val fadeAnimator: ObjectAnimator = if (fadeAnimatorType == FadeAnimatorType.FADE_IN) {
            ObjectAnimator.ofFloat(
                viewToAnimate, AnimationConstants.ViewProperties.ALPHA, 0f, 1f
            )
        } else {
            ObjectAnimator.ofFloat(
                viewToAnimate, AnimationConstants.ViewProperties.ALPHA, 1f, 0f
            )
        }
        fadeAnimator.duration = animatorDuration.toLong()
        return fadeAnimator
    }

    /**
     * Defines the fade animations that could be made.
     */
    enum class FadeAnimatorType {
        FADE_IN
    }

    /**
     * Defines the translate animations that could be made.
     */
    enum class TranslationAnimatorType {
        TRANSLATION_X, TRANSLATION_Y
    }
}