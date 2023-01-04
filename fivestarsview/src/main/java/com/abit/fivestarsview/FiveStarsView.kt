package com.abit.fivestarsview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class FiveStarsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val view = inflate(context, R.layout.layout_five_stars_view, this)
}