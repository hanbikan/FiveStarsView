# FiveStarsView

<p align="center">
  <img src="https://img.shields.io/badge/License-Apache%202.0-brightgreen"/>
  <img src="https://img.shields.io/badge/API-23%2B-green"/>
  <img src="https://img.shields.io/badge/Github-hanbikan-blue"/>
</p>

<p align="center">⭐️ A simple and flexible FiveStarsView, with customizable star images, color, size, and the like.</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/58168528/217256711-3511c0a6-f099-4058-99ee-70bb4379f17c.gif" height="500px"/>
  <img src="https://user-images.githubusercontent.com/58168528/217521907-dd924583-a85d-40ad-97d9-9cc047f12b89.gif" height="500px"/>
</p>


## Including in your project
### Gradle
Add the dependency below to your module's build.gradle file.
```gradle
dependencies {
    implementation "io.github.hanbikan:fivestarsview:1.0.0"
}
```

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
    app:fiveStarsView_starSize="100dp" // the size of the stars.
    app:fiveStarsView_starMargin="10dp" // the margin between each stars.
    app:fiveStarsView_starColor="@color/yellow" // the color of the stars.
    app:fiveStarsView_starRating="5" // star rating value.(float)
    app:fiveStarsView_changeable="true" // enables touch to change the star rating.
    app:fiveStarsView_filledStarDrawable="@drawable/ic_baseline_star_24" // the drawable of the filled star.
    app:fiveStarsView_outlineStarDrawable="@drawable/ic_baseline_star_outline_24" // the drawable of the outline star.
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

#### Two-way Data Binding
You can also apply the two-way data binding with the `fiveStarsView_starRating` attribute.
```gradle
<com.hanbikan.fivestarsview.FiveStarsView
    android:id="@+id/five_stars_view"
    ..
    app:fiveStarsView_starRating="@={vm.starRating}"
/>
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

## FiveStarsView Attributes
|Attributes|Type|Default|Description|
|---|---|---|---|
|fiveStarsView_starSize|dimension|24dp|the size of the stars.|
|fiveStarsView_starMargin|dimension|0dp|the margin between each stars.|
|fiveStarsView_starColor|color|#F1C40f|the color of the stars.|
|fiveStarsView_starRating|float|0.0f|star rating value.|
|fiveStarsView_changeable|boolean|true|enables touch to change the star rating.|
|fiveStarsView_filledStarDrawable|reference|ic_baseline_star_24|the drawable of the filled star.|
|fiveStarsView_outlineStarDrawable|reference|ic_baseline_star_outline_24|the drawable of the outline star.|

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
