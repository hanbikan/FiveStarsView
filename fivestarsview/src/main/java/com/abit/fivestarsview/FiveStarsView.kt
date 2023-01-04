package com.abit.fivestarsview

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout

class FiveStarsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var isAnimated = true
    private var animatorDuration = 200
    private var starRating = 0

    private val view = inflate(context, R.layout.layout_five_stars_view, this)
    private val layoutFront = view.findViewById<ConstraintLayout>(R.id.layout_front)
    private val frontStars: List<AppCompatImageView> = listOf(
        view.findViewById(R.id.image_front_star1),
        view.findViewById(R.id.image_front_star2),
        view.findViewById(R.id.image_front_star3),
        view.findViewById(R.id.image_front_star4),
        view.findViewById(R.id.image_front_star5),
    )
    private val layoutBack = view.findViewById<ConstraintLayout>(R.id.layout_back)
    private val backStars: List<AppCompatImageView> = listOf(
        view.findViewById(R.id.image_back_star1),
        view.findViewById(R.id.image_back_star2),
        view.findViewById(R.id.image_back_star3),
        view.findViewById(R.id.image_back_star4),
        view.findViewById(R.id.image_back_star5),
    )

    init {
        getAttrs(attrs)
        setListener()
        // Note that View.clipBounds equals null in first
        layoutFront.clipBounds = Rect(0, 0, layoutFront.width, layoutFront.height)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FiveStarsView)
        try {
            setTypeArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun setTypeArray(a: TypedArray) {
        /**
         * TODO
         * starSize
         * starBackgroundColor
         * starColor
         * starMargin
         * selectedStarsCount(two-way databinding)
         * changeable
         * starSrc?
         * starRating
         */
        isAnimated = a.getBoolean(R.styleable.FiveStarsView_isAnimated, isAnimated)
        animatorDuration = a.getInt(R.styleable.FiveStarsView_animatorDuration, animatorDuration)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListener() {
        if (isAnimated) {
            layoutBack.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        layoutFront.clipBounds = Rect(0, 0, event.x.toInt(), layoutFront.height)
                        requestLayout()
                    }
                    MotionEvent.ACTION_UP -> {
                        starRating = calculateStarRating(event.rawX)
                        val prevRight = layoutFront.clipBounds.right
                        val nextRight = getRightXByStarRating().toInt()
                        if (prevRight != nextRight) {
                            ValueAnimator.ofInt(prevRight, nextRight).apply {
                                addUpdateListener { valueAnimator ->
                                    layoutFront.clipBounds = Rect(0, 0, valueAnimator.animatedValue as Int, layoutFront.height)
                                }
                                duration = animatorDuration.toLong()
                                start()
                            }
                        }
                    }
                }
                true
            }
        } else {
            layoutBack.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        starRating = calculateStarRating(event.rawX)
                        val prevRight = layoutFront.clipBounds.right
                        val nextRight = getRightXByStarRating().toInt()
                        if (prevRight != nextRight) {
                            layoutFront.clipBounds = Rect(0, 0, nextRight, layoutFront.height)
                        }
                        requestLayout()
                    }
                }
                true
            }
        }
    }

    private fun calculateStarRating(rawX: Float): Int {
        return if (rawX < backStars[0].midX()) 0
        else if (rawX < backStars[1].midX()) 1
        else if (rawX < backStars[2].midX()) 2
        else if (rawX < backStars[3].midX()) 3
        else if (rawX < backStars[4].midX()) 4
        else 5
    }

    private fun getRightXByStarRating(): Float {
        return when (starRating) {
            0 -> backStars[0].x
            1 -> backStars[0].rightX()
            2 -> backStars[1].rightX()
            3 -> backStars[2].rightX()
            4 -> backStars[3].rightX()
            else -> backStars[4].rightX()
        }
    }

    private fun View.rightX() = x + width
    private fun View.midX() = x + width/2
}