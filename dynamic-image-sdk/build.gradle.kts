plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.ragav63.dynamic_image_sdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            // ✅ Enable shrinking and obfuscation
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // ✅ Keep debug builds simple
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ✅ Glide (safe version range between 4.12.0 and <5.0.0)
    compileOnly("com.github.bumptech.glide:glide:4.16.0")
    compileOnly("com.github.Baseflow:PhotoView:2.3.0")

}

// ✅ JitPack-compatible Maven Publish setup
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.Ragav63" // 👈 this must match your GitHub username exactly
                artifactId = "DynamicImageSdk"  // 👈 this is your module name (can be anything)
                version = "1.0.13"               // 👈 update this every release/tag
            }
        }
    }
}
