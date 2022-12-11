package com.example.pokeapp.ui.animations

import android.animation.Animator

/**
 * This class contains helper methods for animations.
 */
object AnimatorUtil {
    /**
     * Merges Animator arrays into one array containing them all.
     *
     * @param animators Animator arrays to merge.
     * @return Array of Animator objects.
     */
    @JvmStatic
    fun concatAnimators(vararg animators: Array<Animator?>): Array<Animator?> {
        // Create array with needed length to allocate all animators.
        val allAnimators = arrayOfNulls<Animator>(getArrayLength(animators))
        var arrayIndex = 0

        // Add each animator to the array
        // y.
        for (animatorArray in animators) {
            for (animator in animatorArray) {
                allAnimators[arrayIndex++] = animator
            }
        }

        // Return the array containing all the animators.
        return allAnimators
    }

    /**
     * Determines the length of an Animator array.
     *
     * @param animators Animator arrays to get length.
     * @return The length of all Animator arrays as a single value.
     */
    private fun getArrayLength(vararg animators: Array<out Array<Animator?>>): Int {
        var length = 0

        // Add each animator array length.
        for (animator in animators) {
            length += animator.size
        }

        // Return length of all animator arrays.
        return length
    }
}