# FiveStarsView

<p align="center">
  <img src="https://img.shields.io/badge/License-Apache%202.0-brightgreen"/>
  <img src="https://img.shields.io/badge/API-23%2B-green"/>
  <img src="https://img.shields.io/badge/GitHub-Hanbit--Kang-blue"/>
</p>

<p align="center">⭐️ A simple and flexible FiveStarsView, whose star images, color, size, and the like are customizable.</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/58168528/217256711-3511c0a6-f099-4058-99ee-70bb4379f17c.gif" width="300px"/>
</p>


## Usage
Add following XML namespace inside your XML layout file.
```gradle
xmlns:app="http://schemas.android.com/apk/res-auto"
```

### FiveStarsView
A basic example of implementing `FiveStarsView` is below.
```gradle
<com.hanbikan.fivestarsview.FiveStarsView
  android:id="@+id/five_stars_view"
  android:layout_width="250dp"
  android:layout_height="70dp"
  app:fiveStarsView_filledStarDrawable="@drawable/ic_baseline_star_24" // the drawable of the filled star.
  app:fiveStarsView_outlineStarDrawable="@drawable/ic_baseline_star_outline_24" // the drawable of the outline star.
  app:fiveStarsView_starSize="100dp" // the size of the stars.
  app:fiveStarsView_starMargin="10dp" // the margin between each stars.
  app:fiveStarsView_starColor="@color/yellow" // the color of the stars.
  app:fiveStarsView_starRating="5" // star rating value.(float)
  app:fiveStarsView_changeable="true" // enables touch to change the star rating.
/>
```

### starRating
`starRating` indicates how many stars should be filled. It muse be a float between `0.0` and `5.0`.

You can get/set the variable as below.
```kotlin
fiveStarsView.getStarRating()
```
```kotlin
fiveStarsView.setStarRating(5.0f)
```

### OnChangeStarRatingListener
`OnChangeStarRatingListener` is called when the `starRating` changes.
```kotlin
fiveStarsView.addOnChangeStarRatingListener(object : FiveStarsView.OnChangeStarRatingListener {
    override fun onChange(starRating: Float) {
        // Put your code here.
    }
})
```

# License
```
Designed and developed by 2022 hanbikan(Hanbit Kang)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
