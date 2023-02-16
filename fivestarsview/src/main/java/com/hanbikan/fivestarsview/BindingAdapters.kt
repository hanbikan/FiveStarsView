package com.hanbikan.fivestarsview

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@BindingAdapter("fiveStarsView_starRating")
fun setStarRating(view: FiveStarsView, starRating: Float) {
    view.setStarRating(starRating)
}

@InverseBindingAdapter(attribute = "fiveStarsView_starRating", event = "starRatingChanged")
fun getStarRating(view: FiveStarsView): Float {
    return view.getStarRating()
}

@BindingAdapter("starRatingChanged")
fun setStarRatingInverseBindingListener(view: FiveStarsView, listener: InverseBindingListener) {
    view.addOnChangeStarRatingListener(
        object: FiveStarsView.OnChangeStarRatingListener {
            override fun onChange(starRating: Float) {
                listener.onChange()
            }
        }
    )
}