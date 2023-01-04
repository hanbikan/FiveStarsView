package com.abit.fivestarsview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout

class FiveStarsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var isAnimated = true

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
         * isAnimated
         * starSrc?
         */
        isAnimated = a.getBoolean(R.styleable.FiveStarsView_isAnimated, isAnimated)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListener() {
        layoutBack.setOnTouchListener { v, event ->
            val right = calculateRightXOfTouchedStar(event.rawX).toInt()
            layoutFront.clipBounds = Rect(0, 0, right, v.height)
            layoutFront.requestLayout()
            true
        }
    }

    private fun calculateRightXOfTouchedStar(rawX: Float): Float {
        return when (rawX) {
            in 0f..backStars[0].x -> backStars[0].x
            in backStars[0].x..backStars[1].x -> backStars[0].x + backStars[0].width
            in backStars[1].x..backStars[2].x -> backStars[1].x + backStars[1].width
            in backStars[2].x..backStars[3].x -> backStars[2].x + backStars[2].width
            in backStars[3].x..backStars[4].x -> backStars[3].x + backStars[3].width
            else -> backStars[4].x + backStars[4].width
        }
    }
}