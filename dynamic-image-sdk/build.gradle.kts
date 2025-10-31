plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.ragav63.dynamic_image_sdk"
    compileSdk = 34 // ✅ correct syntax

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ✅ Glide dependency
    implementation("com.github.bumptech.glide:glide:4.16.0")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.ragav63"
            artifactId = "dynamic-image-sdk"
            version = "1.0.0"

            // ✅ Use official release component
            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/YOUR_GITHUB_USERNAME/YOUR_REPOSITORY_NAME")
            credentials {
                username = project.findProperty("gpr.user") as String?
                    ?: System.getenv("USERNAME_GITHUB")
                password = project.findProperty("gpr.key") as String?
                    ?: System.getenv("TOKEN_GITHUB")
            }
        }
    }
}
