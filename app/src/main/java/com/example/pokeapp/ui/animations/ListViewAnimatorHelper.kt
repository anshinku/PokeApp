package com.example.pokeapp.ui.animations

import android.animation.Animator
import android.util.SparseArray
import androidx.recyclerview.widget.LinearLayoutManager
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import android.animation.AnimatorSet
import android.os.SystemClock
import android.view.View
import android.widget.ListView

/**
 * This is a helper class for animating rows inside a ListView.
 */
class ListViewAnimatorHelper
/**
 * Constructor.
 *
 * @param context       Application context.
 * @param layoutManager Layout Manager which determines how rows are being displayed.
 */(context: Activity?, layoutManager: RecyclerView.LayoutManager?) {
    /**
     * The active Animators. Keys are hashcode of the Views that are animated.
     */
    private val mAnimators = SparseArray<Animator>()

    // Variables.
    private var lastAnimatedPosition = 0
    private var firstAnimatedPosition = 0
    private var animationDuration = 0
    private var animationDelay = 0
    private var animationInitialDelay = 0
    private var animationStart: Long = 0

    // Views.
    private var listView: ListView? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    // Application context.
    private var context: Activity

    init {
        this.context = context!!
        linearLayoutManager = layoutManager as LinearLayoutManager?
        initializeVariables()
    }

    /**
     * Initializes class level variables.
     */
    private fun initializeVariables() {
        firstAnimatedPosition = -1
        lastAnimatedPosition = firstAnimatedPosition
        animationStart = -1
        setAnimationDuration(context.resources.getInteger(R.integer.sv_list_view_items_translate_animation_duration))
        setAnimationDelay(context.resources.getInteger(R.integer.sv_list_view_items_translate_animation_offset))
        setAnimationInitialDelay(context.resources.getInteger(R.integer.sv_list_view_items_translate_animation_initial_delay))
    }

    // Setters.
    private fun setAnimationDuration(animationDuration: Int) {
        this.animationDuration = animationDuration
    }

    private fun setAnimationDelay(animationDelay: Int) {
        this.animationDelay = animationDelay
    }

    private fun setAnimationInitialDelay(animationInitialDelay: Int) {
        this.animationInitialDelay = animationInitialDelay
    }

    /**
     * Animates given View if necessary.
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     */
    fun animateViewIfNecessary(position: Int, view: View, animators: Array<Animator?>) {
        if (position > lastAnimatedPosition) {
            if (firstAnimatedPosition == -1) {
                firstAnimatedPosition = position
            }
            cancelExistingAnimation(view)
            animateView(position, view, animators)
            lastAnimatedPosition = position
        }
    }

    /**
     * Animates given View.
     *
     * @param view the View that should be animated.
     */
    private fun animateView(position: Int, view: View, animators: Array<Animator?>) {
        if (animationStart == -1L) {
            animationStart = SystemClock.uptimeMillis()
        }
        view.alpha = 0f
        val set = AnimatorSet()
        set.playTogether(*animators)
        set.startDelay = calculateAnimationDelay(position).toLong()
        set.duration = animationDuration.toLong()
        set.start()
        mAnimators.put(view.hashCode(), set)
    }

    /**
     * Returns the delay in milliseconds after which animation for View with
     * position mLastAnimatedPosition + 1 should start.
     */
    private fun calculateAnimationDelay(position: Int): Int {
        val delay: Int
        val lastVisiblePosition =
            if (listView != null) listView!!.lastVisiblePosition else linearLayoutManager!!.findLastVisibleItemPosition()
        val firstVisiblePosition =
            if (listView != null) listView!!.firstVisiblePosition else linearLayoutManager!!.findFirstVisibleItemPosition()
        val numberOfItemsOnScreen = lastVisiblePosition - firstVisiblePosition
        val numberOfAnimatedItems = position - 1 - firstAnimatedPosition
        delay = if (numberOfItemsOnScreen + 1 < numberOfAnimatedItems) {
            animationDelay
        } else {
            val delaySinceStart = (position - firstAnimatedPosition) * animationDelay
            0.coerceAtLeast((-SystemClock.uptimeMillis() + animationStart + animationInitialDelay + delaySinceStart).toInt())
        }
        return delay
    }

    /**
     * Cancels any existing animations for given View.
     */
    private fun cancelExistingAnimation(view: View) {
        val hashCode = view.hashCode()
        val animator = mAnimators[hashCode]
        if (animator != null) {
            animator.end()
            mAnimators.remove(hashCode)
        }
    }

}