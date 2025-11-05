# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# ================================================
# ✅ SDK ProGuard Rules — DynamicImageSDK
# ================================================

# --- Keep Glide generated API (required) ---
-keep class com.bumptech.glide.** { *; }
-keep class * extends com.bumptech.glide.module.AppGlideModule

# --- Keep PhotoView (required) ---
-keep class com.github.chrisbanes.photoview.** { *; }
-keep class com.github.Baseflow.photoview.** { *; }

# --- General cleanup & optimization ---
-dontnote
-dontwarn

# --- Keep only your SDK’s public API ---
-keep public class com.ragav63.dynamic_image_sdk.** { *; }

# --- Let R8 strip all unused library internals ---
-keep class * {
    public protected *;
}

# --- Optional: Keep source file names for better stacktraces ---
# -keepattributes SourceFile,LineNumberTable
# -renamesourcefileattribute SourceFile