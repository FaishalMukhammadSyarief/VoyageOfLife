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
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
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

    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    testImplementation ("org.mockito:mockito-core:5.1.1")
    testImplementation ("org.mockito:mockito-inline:5.1.1")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")

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

    /*  MAPS  */
    implementation (libs.play.services.maps)
    implementation (libs.play.services.maps)
    implementation (libs.play.services.location)

}