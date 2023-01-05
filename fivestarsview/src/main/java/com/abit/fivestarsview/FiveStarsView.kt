package com.abit.fivestarsview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw

class FiveStarsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

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
    }


    fun setStarRating(newStarRating: Int) {
        Log.e(starRating.toString(), newStarRating.toString())
        if (starRating != newStarRating) {
            starRating = newStarRating

            backStars.last().doOnLayout {
                layoutFront.doOnLayout {
                    val nextRight = getRightXByStarRating().toInt()
                    layoutFront.clipBounds = Rect(0, 0, nextRight, layoutFront.height)
                    layoutFront.requestLayout()
                }
            }
        }
    }

    fun getStarRating() = starRating


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
         * changeable
         * starSrc?
         * starRating
         */
        animatorDuration = a.getInt(R.styleable.FiveStarsView_fiveStarsView_animatorDuration, animatorDuration)
        setStarRating(a.getInt(R.styleable.FiveStarsView_fiveStarsView_starRating, starRating))
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListener() {
        layoutBack.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    setStarRating(calculateStarRating(event.rawX))
                }
            }
            true
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