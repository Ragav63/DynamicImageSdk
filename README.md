# Dynamic Image SDK 🖼️

Easily display dynamic image grids with optional full-screen dialogs and Jetpack Compose support.
Flexible for any Android project — handles URLs, resource IDs, and file paths seamlessly.

# 🚀 What’s New in v1.0.14
Jetpack Compose support

# 🚀 What’s New in v1.0.12

Added showDefaultDialog flag – optionally open a built-in full-screen image preview on click

Added scaleType parameter – control image scaling directly (e.g., FIT_XY, CENTER_CROP, etc.)

Improved layout performance – optimized grid rendering and smoother scaling behavior

# 🚀 What’s New in v1.0.11

Added showDefaultDialog flag – optionally open a built-in full-screen image preview on click

Added scaleType parameter – control image scaling directly (e.g., FIT_XY, CENTER_CROP, etc.)

Improved layout performance – optimized grid rendering and smoother scaling behavior

## Features ✨

- **🚀 Dynamic Image Loading**: Load images from various sources (URL, resources, assets)
- **⚡ Smart Caching**: Uses Glide for efficient memory and disk caching
- **🎨 Flexible Scaling**: Supports all ImageView.ScaleType options
- **🧩 Built-in Dialog Viewer**: Instantly preview images in an interactive dialog
- **🛡️ Error & Placeholder Handling**: Custom images for both
- **🧠 Kotlin-First**: Clean Kotlin implementation, easy Java interoperability
- **🧱 Jetpack Compose**: support (from v1.0.14)
- **🧱 Lightweight** – Ultra-lightweight — just 96 KB (v1.0.12)

## Installation 📦

### Add JitPack Repository

Add the JitPack repository to your root `build.gradle`:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

# Add Dependency -v1.0.14
## 🎨 Jetpack Compose Support (since v1.0.14)

Now you can use the SDK directly in Compose!
```
DynamicImageGridCompose(
    imageList = listOf(
        R.drawable.tvshows,
        R.drawable.gots01e01,
        R.drawable.gots01e03
    ),
    showDialog = true,
    dialogTitle = "Dialog Images"
)
```

```
dependencies {
    implementation 'com.github.Ragav63:DynamicImageSdk:v1.0.14'
}
```

# Add Dependency -v1.0.12
```
dependencies {
    implementation 'com.github.Ragav63:DynamicImageSdk:v1.0.12'
}
```
✅ v1.0.12 — latest version (optimized AAR size ≈ 96 KB, same features as v1.0.11).

# Add Dependency - v1.0.11

Add the dependency to your module's build.gradle:

```
gradle
dependencies {
    implementation 'com.github.Ragav63:DynamicImageSdk:v1.0.11'
}
```
💡 Note :v1.0.11 is the latest version featuring a new dialog-based image viewer and improved grid handling.


# Screenshots 📸
## 🧩 Version v1.0.8

Visual layout from version v1.0.8 (stable) — initial grid design and image layout.

<img src="https://github.com/user-attachments/assets/5191e737-2f15-4fd9-a64a-f2b0d042791e" alt="v1.0.8 Screenshot" height="300" width="200"/>

## ⚙️ Version v1.0.9

Enhanced visuals in v1.0.9 — improved alignment, better aspect ratio handling, and smoother transitions.

<img src="https://github.com/user-attachments/assets/35329285-669e-4ea0-ac57-979ea5142d76" alt="v1.0.8 Screenshot" height="300" width="200"/>


# Usage 🚀
## XML Layout
Add DynamicImageGridView to your layout:

xml
```
<com.ragav63.dynamic_image_sdk.DynamicImageGridView
    android:id="@+id/gridImages"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

## Basic Implementation
kotlin
```
val gridImage = findViewById<DynamicImageGridView>(R.id.gridImages)

// Set images from drawable resources
gridImage.setImages(listOf(
    R.drawable.image1,
    R.drawable.image2,
    R.drawable.image3,
    R.drawable.image4,
    R.drawable.image5,
    R.drawable.image6
))

// Set click listener
gridImage.setOnImageClickListener(object : OnImageClickListener {
    override fun onImageClick(index: Int, imageUrl: Any, allImages: List<Any>) {
        Log.d("ImageClick", "Clicked index=$index, url=$imageUrl, total=${allImages.size}")
        // Handle image click - open full screen, show details, etc.
    }
})
```

## Loading from URLs
kotlin
```
val imageUrls = listOf(
    "https://example.com/image1.jpg",
    "https://example.com/image2.jpg",
    "https://example.com/image3.jpg",
    "https://example.com/image4.jpg",
    "https://example.com/image5.jpg",
    "https://example.com/image6.jpg"
)

gridImage.setImages(imageUrls)
```

## Mixed Sources
kotlin
```
val mixedImages = listOf(
    "https://example.com/remote1.jpg",  // URL
    R.drawable.local_image,             // Drawable resource
    "https://example.com/remote2.jpg",  // URL
    R.drawable.another_local            // Drawable resource
)

gridImage.setImages(mixedImages)
```

# Layout Patterns 🔄
The SDK automatically handles different layouts:

1 Image: Full width single column

2 Images: Equal two-column layout

3 Images: Equal three-column layout

4 Images: 2x2 grid layout

5+ Images: Special 2-3 grid with overlay for extra images

## ⚙️ Version v1.0.12
Enhanced visuals in v1.0.12 — Optimized AAR (removed unused deps), same features as v1.0.11 and also given dialog title.

# Screenshots 📸
<img src="https://github.com/user-attachments/assets/c464986e-0f80-477c-9f43-d0afa15ab269" alt="v1.0.8 Screenshot" height="300" width="200"/>

Example
```
gridImage.setImages(
    listOf(
        R.drawable.tvshows,
        R.drawable.gots01e01,
        R.drawable.gots01e03,
        R.drawable.gots01e04,
        R.drawable.gots01e05,
        R.drawable.theboys
    ),
    true, // enable built-in dialog
    ImageView.ScaleType.FIT_XY // optional scale type,
    "Dialog"
)
```

# 🧠 Built-in Dialog Viewer (v1.0.11)

Version v1.0.11 introduces an elegant, dialog-based image preview viewer that replaces the need for a fullscreen activity.

## ⚙️ Version v1.0.11
Enhanced visuals in v1.0.11 — introduces an elegant, dialog-based image preview viewer that replaces the need for a fullscreen activity.
If the second parameter (showDefaultDialog) is set to true, the SDK automatically opens its built-in image viewer dialog when an image is clicked.
If you set it to false or omit the third parameter (scaleType), the dialog will not appear — allowing you to handle clicks manually.

# Screenshots 📸
<img src="https://github.com/user-attachments/assets/c464986e-0f80-477c-9f43-d0afa15ab269" alt="v1.0.8 Screenshot" height="300" width="200"/>

Example
```
gridImage.setImages(
    listOf(
        R.drawable.tvshows,
        R.drawable.gots01e01,
        R.drawable.gots01e03,
        R.drawable.gots01e04,
        R.drawable.gots01e05,
        R.drawable.theboys
    ),
    true, // enable built-in dialog
    ImageView.ScaleType.FIT_XY // optional scale type
)
```

Example (Custom Click Handling)
```
gridImage.setImages(
    listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    ),
    false // disable default dialog
)

// Set click listener
gridImage.setOnImageClickListener(object : OnImageClickListener {
    override fun onImageClick(index: Int, imageUrl: Any, allImages: List<Any>) {
        Log.d("ImageClick", "Clicked index=$index, url=$imageUrl, total=${allImages.size}")
        // Handle image click manually - open full screen, show details, etc.
    }
})
```

## 🪟 Dialog Preview Features

Displays in a centered dialog (80% width × 50% height)

Horizontal scrollable list with zoomable PhotoView images

Thumbnails for quick navigation

Previous/Next buttons for control

Optional title via dialogTitle

Custom scale type for each image (e.g., FIT_CENTER, CENTER_CROP, FIT_XY)

## ⚙️ Parameters Summary
Parameter	Type	Default	Description
imageUrls	List<Any>	Required	List of image sources (URLs, Drawables, etc.)
showDefaultDialog	Boolean	false	Whether to open the SDK’s built-in dialog viewer
scaleTypeValue	ImageView.ScaleType?	CENTER_CROP	Defines image scaling inside both grid and dialog

## 🧩 Custom Dialog Usage
```
val dialog = ImageOverviewDialog(
    context = context,
    allImages = imageList,
    dialogTitle = "Season Gallery",
    scaleType = ImageView.ScaleType.FIT_CENTER
)
dialog.showDialog()
```

The dialog includes synchronized preview and thumbnail lists, powered by RecyclerView and Glide.

# API Reference 📚
## DynamicImageGridView Methods
Method	Description
setImages(imageUrls: List<Any>)	Set images to display (URLs, resource IDs, or mixed)
setOnImageClickListener(listener: OnImageClickListener)	Set click listener for images
OnImageClickListener Interface
kotlin
```
interface OnImageClickListener {
    fun onImageClick(index: Int, imageUrl: Any, allImages: List<Any>)
}
```
# Parameters:

index: Position of clicked image (0-based)

imageUrl: The image source (URL, resource ID, etc.)

allImages: Complete list of all images passed to the grid

## Programmatic Creation

kotlin
```
val gridView = DynamicImageGridView(context).apply {
    layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    setImages(yourImageList)
    setOnImageClickListener(yourClickListener)
}

// Add to your layout
parentLayout.addView(gridView)
```

# ProGuard/R8 🔒
If you're using ProGuard, add these rules to your proguard-rules.pro:

proguard
```
-keep class com.ragav63.dynamic_image_sdk.** { *; }
-dontwarn com.ragav63.dynamic_image_sdk.**
```

# Requirements 📋
Min SDK: 21 (Android 5.0)

Compile SDK: 34

Kotlin: 1.9+

Java: 17

# Dependencies 📚
This SDK uses:
PhotoView : zoom support

Glide: For image loading and caching

AndroidX: For compatibility and modern Android features

# Version History 📖
✅ Working Versions:
v1.0.14 - ✅ Jetpack Compose support (DynamicImageGridCompose)

v1.0.12 - 🟢 96 KB Optimized AAR (removed unused deps), same features as v1.0.11

v1.0.11 - ✅ Latest Added dialog-based image viewer, scaleType support, removed ViewBinding

v1.0.9 - ✅ Latest Enhanced grid alignment, smoother animations, improved caching

v1.0.8 - ✅ Latest stable version (Recommended)

v1.0.2 - 🟡 Stable release

v1.0.0 - 🔰 Initial release

❌ Other versions may not work properly. Please use only the versions listed above.

# License 📄
text
Copyright 2025 Ragav

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
Contributing 🤝
Contributions are welcome! Please feel free to submit a Pull Request.

# Reporting Issues 🐛
If you find any issues, please report them on the GitHub Issues page.

# Made with ❤️ by Ragav
