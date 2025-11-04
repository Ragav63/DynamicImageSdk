# Dynamic Image SDK 🖼️

A powerful Android SDK for dynamic image loading, transformation, and caching with support for placeholders, error handling, and custom transformations.

## Features ✨

- **Dynamic Image Loading**: Load images from various sources (URL, resources, assets)
- **Smart Caching**: Memory and disk caching for optimal performance
- **Transformations**: Apply various image transformations on-the-fly
- **Placeholder Support**: Custom placeholders while loading
- **Error Handling**: Custom error images for failed loads
- **Kotlin First**: Written in Kotlin with coroutines support
- **Lightweight**: Minimal footprint with no unnecessary dependencies

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

# Add Dependency

Add the dependency to your module's build.gradle:

```
gradle
dependencies {
    implementation 'com.github.Ragav63:DynamicImageSdk:v1.0.9'
}
```
💡 Note: v1.0.9 is the latest release with improved grid rendering and optimized image loading logic.

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

# Advanced Usage 🛠️
## Handling Clicks
kotlin
```
gridImage.setOnImageClickListener(object : OnImageClickListener {
    override fun onImageClick(index: Int, imageUrl: Any, allImages: List<Any>) {
        when (imageUrl) {
            is String -> {
                // Handle URL image
                openFullScreenImage(imageUrl, allImages)
            }
            is Int -> {
                // Handle resource ID
                openFullScreenResource(imageUrl, allImages)
            }
        }
    }
    
    private fun openFullScreenImage(url: String, allImages: List<Any>) {
        // Implement full screen viewer
    }
    
    private fun openFullScreenResource(resId: Int, allImages: List<Any>) {
        // Implement full screen viewer for resources
    }
})
```
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

Glide: For image loading and caching

AndroidX: For compatibility and modern Android features

# Version History 📖
✅ Working Versions:

v1.0.9 - Latest Enhanced grid alignment, smoother animations, improved caching

v1.0.8 - Latest stable version (Recommended)

v1.0.2 - Stable release

v1.0.0 - Initial release

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
