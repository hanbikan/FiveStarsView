package com.abit.fivestarsview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fiveStarsView = findViewById<FiveStarsView>(R.id.five_stars_view)
        val testButton = findViewById<Button>(R.id.button_test)
        testButton.setOnClickListener {
            fiveStarsView.setFilledStarDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
            fiveStarsView.setOutlineStarDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24))
        }
    }
}