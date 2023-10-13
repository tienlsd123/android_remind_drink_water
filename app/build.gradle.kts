plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.bxt.reminddrinkwater"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bxt.reminddrinkwater"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding.isEnabled = true
    buildFeatures.dataBinding = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    /* ktx */
    implementation(libs.androidx.collection.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    /* splash */
    implementation(libs.androidx.core.splashscreen)

    /* hilt */
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    /* navigation */
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    /* coil */
    implementation(libs.coil)

    /* room */
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)

    /* work */
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.work.multiprocess)

    /* datastore */
    implementation(libs.androidx.datastore.preferences)
}

kapt {
    correctErrorTypes = true
}
