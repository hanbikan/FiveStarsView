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
import java.lang.Float.max
import java.lang.Float.min

private const val DEFAULT = -1
private const val MIN_STAR_RATING = 0f
private const val MAX_STAR_RATING = 5f

class FiveStarsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var starRating = 0.0f
    private var starSize = DEFAULT
    private var starColor = DEFAULT
    private var starMargin = 0
    private var changeable = true

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
    }


    fun setStarRating(newStarRating: Float) {
        val starRatingInRange = max(min(newStarRating, MAX_STAR_RATING), MIN_STAR_RATING)
        if (starRating != starRatingInRange) {
            starRating = starRatingInRange

            backStars.last().doOnLayout {
                layoutFront.doOnLayout {
                    val right = (backStars[0].x + backStars[4].rightX()) * (starRating / MAX_STAR_RATING)
                    layoutFront.clipBounds = Rect(0, 0, right.toInt(), layoutFront.height)
                    layoutFront.requestLayout()
                }
            }
        }
    }

    fun getStarRating() = starRating

    fun setStarSize(size: Int) {
        starSize = size
        if (starSize != DEFAULT) {
            frontStars.forEach {
                it.layoutParams = it.layoutParams.apply { width = starSize }
            }
            backStars.forEach {
                it.layoutParams = it.layoutParams.apply { width = starSize }
            }
        }
    }

    fun getStarSize() = starSize

    fun setStarColor(color: Int) {
        starColor = color
        if (starColor != DEFAULT) {
            frontStars.forEach {
                it.setColorFilter(starColor)
            }
            backStars.forEach {
                it.setColorFilter(starColor)
            }
        }
    }

    fun getStarColor() = starColor

    fun setStarMargin(margin: Int) {
        starMargin = margin
        frontStars.forEach {
            it.layoutParams = (it.layoutParams as MarginLayoutParams).apply {
                marginStart = starMargin / 2
                marginEnd = starMargin / 2
            }
        }
        backStars.forEach {
            it.layoutParams = (it.layoutParams as MarginLayoutParams).apply {
                marginStart = starMargin / 2
                marginEnd = starMargin / 2
            }
        }
    }

    fun getStarMargin() = starMargin

    @SuppressLint("ClickableViewAccessibility")
    fun setChangeable(flag: Boolean) {
        changeable = flag
        if (changeable) {
            layoutBack.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        setStarRating(calculateStarRating(event.rawX))
                    }
                }
                true
            }
        }
    }

    fun getChangeable() = changeable


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
         * changeable
         * starSrc?
         * starRating: two-way databinding
         */
        setStarRating(a.getFloat(R.styleable.FiveStarsView_fiveStarsView_starRating, starRating))
        setStarSize(a.getDimensionPixelSize(R.styleable.FiveStarsView_fiveStarsView_starSize, starSize))
        setStarColor(a.getColor(R.styleable.FiveStarsView_fiveStarsView_starColor, starColor))
        setStarMargin(a.getDimensionPixelSize(R.styleable.FiveStarsView_fiveStarsView_starMargin, starMargin))
        setChangeable(a.getBoolean(R.styleable.FiveStarsView_fiveStarsView_changeable, changeable))
    }

    private fun calculateStarRating(rawX: Float): Float {
        return if (rawX < backStars[0].midX()) 0f
        else if (rawX < backStars[1].midX()) 1f
        else if (rawX < backStars[2].midX()) 2f
        else if (rawX < backStars[3].midX()) 3f
        else if (rawX < backStars[4].midX()) 4f
        else 5f
    }

    private fun View.rightX() = x + width
    private fun View.midX() = x + width/2
}