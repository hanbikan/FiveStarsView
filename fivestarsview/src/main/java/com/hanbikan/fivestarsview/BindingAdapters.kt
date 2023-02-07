package com.hanbikan.fivestarsview

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@BindingAdapter("starRating")
fun setStarRating(view: FiveStarsView, starRating: Float) {
    view.setStarRating(starRating)
}

@BindingAdapter("starRatingChanged")
fun setStarRatingInverseBindingListener(view: FiveStarsView, listener: InverseBindingListener) {
    view.addOnChangeStarRatingListener(
        object: FiveStarsView.OnChangeStarRatingListener {
            override fun onChange() {
                listener.onChange()
            }
        }
    )
}

@InverseBindingAdapter(attribute = "starRating", event = "starRatingChanged")
fun getStarRating(view: FiveStarsView): Float {
    return view.getStarRating()
}