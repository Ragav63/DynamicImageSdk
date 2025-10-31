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
    implementation 'com.github.Ragav63:DynamicImageSdk:v1.0.8'
}
```

# Usage 🚀
## Basic Image Loading
```
kotlin
import com.ragav.dynamicimagesdk.DynamicImageSdk

// Load image from URL
DynamicImageSdk.load(imageView, "https://example.com/image.jpg")

// Load with placeholder
DynamicImageSdk.load(
    imageView = imageView,
    url = "https://example.com/image.jpg",
    placeholder = R.drawable.placeholder
)

// Load with error image
DynamicImageSdk.load(
    imageView = imageView,
    url = "https://example.com/image.jpg",
    placeholder = R.drawable.placeholder,
    errorImage = R.drawable.error_image
)
```

## Advanced Configuration

```
kotlin
DynamicImageSdk.load(
    imageView = imageView,
    url = "https://example.com/image.jpg",
    placeholder = R.drawable.placeholder,
    errorImage = R.drawable.error_image,
    applyCircleCrop = true,  // Apply circle transformation
    applyGrayScale = false   // Apply grayscale filter
)
```

## From Resources
```
kotlin
DynamicImageSdk.load(
    imageView = imageView,
    resourceId = R.drawable.local_image
)

From Assets
kotlin
DynamicImageSdk.load(
    imageView = imageView,
    assetPath = "images/asset_image.png"
)
```

# Configuration ⚙️
Initialize (Optional)
The SDK auto-initializes, but you can customize the configuration:
```
kotlin
// Custom initialization (if needed)
DynamicImageSdk.initialize(
    cacheSize = 50 * 1024 * 1024, // 50MB cache
    timeout = 30 // 30 seconds timeout
)
```

# Available Parameters
Parameter	Type	Description	Default
url	String	Image URL to load	-
resourceId	Int	Android resource ID	-
assetPath	String	Path to asset image	-
placeholder	Int	Placeholder drawable resource	null
errorImage	Int	Error drawable resource	null
applyCircleCrop	Boolean	Apply circle crop transformation	false
applyGrayScale	Boolean	Apply grayscale filter	false
ProGuard/R8 🔒
If you're using ProGuard, add these rules to your proguard-rules.pro:
```
proguard
-keep class com.ragav.dynamicimagesdk.** { *; }
-dontwarn com.ragav.dynamicimagesdk.**
Requirements 📋
Min SDK: 21 (Android 5.0)

Compile SDK: 34

Kotlin: 1.9+

Java: 17
```

# Dependencies 📚
This SDK uses:

Glide: For image loading and caching

Kotlin Coroutines: For asynchronous operations

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

# Version History 📖
✅ Working Versions:

v1.0.8 - Latest stable version (Recommended)

v1.0.2 - Stable release

v1.0.0 - Initial release

❌ Other versions may not work properly. Please use only the versions listed above.

Made with ❤️ by Ragav
