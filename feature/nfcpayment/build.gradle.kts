plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.paymorecase.nfcpayment"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Core Project Modules
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.ui)

    // Compose dependencies can be accessed through the `:core:ui` module

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Navigation Component
    implementation(libs.navigation.compose)

    // KotlinX Serialization
    implementation(libs.kotlinx.serialization.json)

    // CameraX & ML Kit
    implementation(libs.mlkit.barcode.scanning)
    implementation(libs.camera.mlkit.vision)
    implementation(libs.androidx.camera.core)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    // QR Code Scanner
    implementation(libs.com.github.yuriy.budiyev)

    implementation(libs.nfc.helper)

}