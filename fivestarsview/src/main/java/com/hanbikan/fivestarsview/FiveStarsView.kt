package com.hanbikan.fivestarsview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
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
    private var filledStarDrawable: Drawable? = null
    private var outlineStarDrawable: Drawable? = null

    private var onChangeStarRatingListeners: MutableList<OnChangeStarRatingListener> = mutableListOf()

    private val view = inflate(context, R.layout.layout_five_stars_view, this)
    private val filledLayout = view.findViewById<ConstraintLayout>(R.id.layout_filled)
    private val filledStars: List<AppCompatImageView> = listOf(
        view.findViewById(R.id.image_filled_star1),
        view.findViewById(R.id.image_filled_star2),
        view.findViewById(R.id.image_filled_star3),
        view.findViewById(R.id.image_filled_star4),
        view.findViewById(R.id.image_filled_star5),
    )
    private val outlineLayout = view.findViewById<ConstraintLayout>(R.id.layout_outline)
    private val outlineStars: List<AppCompatImageView> = listOf(
        view.findViewById(R.id.image_outline_star1),
        view.findViewById(R.id.image_outline_star2),
        view.findViewById(R.id.image_outline_star3),
        view.findViewById(R.id.image_outline_star4),
        view.findViewById(R.id.image_outline_star5),
    )

    init {
        getAttrs(attrs)
    }


    fun getStarRating() = starRating

    fun setStarRating(newStarRating: Float) {
        val starRatingInRange = max(min(newStarRating, MAX_STAR_RATING), MIN_STAR_RATING)
        starRating = starRatingInRange

        outlineStars.last().doOnLayout {
            filledLayout.doOnLayout {
                val right = (outlineStars[0].x + outlineStars[4].rightX()) * (starRating / MAX_STAR_RATING)
                filledLayout.clipBounds = Rect(0, 0, right.toInt(), filledLayout.height)
                filledLayout.requestLayout()
            }
        }

        onChangeStarRatingListeners.forEach { it.onChange(newStarRating) }
    }

    fun getStarSize() = starSize

    fun setStarSize(size: Int) {
        starSize = size
        if (starSize != DEFAULT) {
            filledStars.forEach {
                it.layoutParams = it.layoutParams.apply { width = starSize }
            }
            outlineStars.forEach {
                it.layoutParams = it.layoutParams.apply { width = starSize }
            }
        }
    }

    fun getStarColor() = starColor

    fun setStarColor(color: Int) {
        starColor = color
        if (starColor != DEFAULT) {
            filledStars.forEach {
                it.setColorFilter(starColor)
            }
            outlineStars.forEach {
                it.setColorFilter(starColor)
            }
        }
    }

    fun getStarMargin() = starMargin

    fun setStarMargin(margin: Int) {
        starMargin = margin
        filledStars.forEach {
            it.layoutParams = (it.layoutParams as MarginLayoutParams).apply {
                marginStart = starMargin / 2
                marginEnd = starMargin / 2
            }
        }
        outlineStars.forEach {
            it.layoutParams = (it.layoutParams as MarginLayoutParams).apply {
                marginStart = starMargin / 2
                marginEnd = starMargin / 2
            }
        }
    }

    fun getChangeable() = changeable

    @SuppressLint("ClickableViewAccessibility")
    fun setChangeable(flag: Boolean) {
        changeable = flag
        if (changeable) {
            outlineLayout.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        setStarRating(calculateStarRating(event.x))
                    }
                }
                true
            }
        }
    }

    fun getFilledStarDrawable() = filledStarDrawable

    fun setFilledStarDrawable(drawable: Drawable?) {
        filledStarDrawable = drawable
        if (drawable != null) {
            filledStars.forEach {
                it.setImageDrawable(drawable)
            }
        }
    }

    fun getOutlineStarDrawable() = outlineStarDrawable

    fun setOutlineStarDrawable(drawable: Drawable?) {
        outlineStarDrawable = drawable
        if (drawable != null) {
            outlineStars.forEach {
                it.setImageDrawable(drawable)
            }
        }
    }

    fun addOnChangeStarRatingListener(listener: OnChangeStarRatingListener) {
        onChangeStarRatingListeners.add(listener)
    }

    fun addOnChangeStarRatingListener(listener: (Float) -> Unit) {
        onChangeStarRatingListeners.add(
            object : OnChangeStarRatingListener {
                override fun onChange(starRating: Float) {
                    listener(starRating)
                }
            }
        )
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
        setStarRating(a.getFloat(R.styleable.FiveStarsView_fiveStarsView_starRating, starRating))
        setStarSize(a.getDimensionPixelSize(R.styleable.FiveStarsView_fiveStarsView_starSize, starSize))
        setStarColor(a.getColor(R.styleable.FiveStarsView_fiveStarsView_starColor, starColor))
        setStarMargin(a.getDimensionPixelSize(R.styleable.FiveStarsView_fiveStarsView_starMargin, starMargin))
        setChangeable(a.getBoolean(R.styleable.FiveStarsView_fiveStarsView_changeable, changeable))
        setFilledStarDrawable(a.getDrawable(R.styleable.FiveStarsView_fiveStarsView_filledStarDrawable))
        setOutlineStarDrawable(a.getDrawable(R.styleable.FiveStarsView_fiveStarsView_outlineStarDrawable))
    }

    private fun calculateStarRating(x: Float): Float {
        return if (x < outlineStars[0].midX()) 0f
        else if (x < outlineStars[1].midX()) 1f
        else if (x < outlineStars[2].midX()) 2f
        else if (x < outlineStars[3].midX()) 3f
        else if (x < outlineStars[4].midX()) 4f
        else 5f
    }

    private fun View.rightX() = x + width
    private fun View.midX() = x + width/2


    interface OnChangeStarRatingListener {
        /**
         * Called when [starRating] changes.
         */
        fun onChange(starRating: Float)
    }
}