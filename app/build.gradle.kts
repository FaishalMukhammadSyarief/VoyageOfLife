plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.zhalz.voyageoflife"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zhalz.voyageoflife"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    /*  TEST  */
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    /*  VIEW MODEL & LIVE DATA  */
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    /*  DAGGER HILT  */
    implementation(libs.hilt.android)
    kapt (libs.hilt.compiler)

    /*  RETROFIT  */
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    /*  GLIDE  */
    implementation(libs.glide)

    /*  LOTTIE  */
    implementation (libs.lottie)

    /*  SWIPE REFRESH  */
    implementation (libs.androidx.swiperefreshlayout)

    /*  DATA STORE  */
    implementation(libs.androidx.datastore.preferences)

    /*  PAGING 3  */
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.common.android)

}