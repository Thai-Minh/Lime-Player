plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.limeplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.limeplayer"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        multiDexEnabled = true
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.multidex)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.runtime)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.savedstate)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.compose.ui.util)
    implementation(libs.androidx.lifecycle.process)

    implementation(libs.appcompat)

    // coil image
    implementation(libs.coil)
    implementation(libs.coil.video)

    // permission
    implementation(libs.permissions.helper)
    implementation(libs.permissions.helper.ktx)

    // navigation
    implementation(libs.navigation.compose)

    // File locker
    implementation(libs.filelocker)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // ExoPlayer
    implementation(libs.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    // DataStore
    implementation(libs.datastore.preferences)

    // Dark Mode WebView
    implementation(libs.webkit)

    // Room Database
    implementation(libs.room.runtime)
//    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(project(":manager"))
    implementation(project(":catcher"))

    // load image drawable
    implementation(libs.accompainist.drawable.painter)

    // For test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
}