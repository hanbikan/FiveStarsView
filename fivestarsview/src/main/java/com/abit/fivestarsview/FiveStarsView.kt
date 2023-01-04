package com.abit.fivestarsview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout

class FiveStarsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val view = inflate(context, R.layout.layout_five_stars_view, this)
    private val layoutFront = view.findViewById<ConstraintLayout>(R.id.layout_front)
    private val layoutBack = view.findViewById<ConstraintLayout>(R.id.layout_back)

    init {
        getAttrs(attrs)
        setListener()
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FiveStarsView)
        try {
            setTypedArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun setTypedArray(a: TypedArray) {
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
    }

    private fun setListener() {
        layoutBack.setOnTouchListener { v, event ->
            layoutFront.clipBounds = Rect(0, 0, event.x.toInt(), v.height)
            layoutFront.requestLayout()
            true
        }
    }
}