@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.hcapps.xpenzave"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.hcapps.xpenzave"
//        minSdk = 24
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
//            buildConfigField("Boolean", "DEBUG", "true")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
//            buildConfigField("Boolean", "DEBUG", "false")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    kotlin {
        jvmToolchain(8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources {
            resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

}

dependencies {

    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.2")

    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-graphics")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.core:core-ktx:1.10.1")

    // lifecycle
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")


    // material icons
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")

    // timber
    implementation ("com.jakewharton.timber:timber:5.0.1")

    // navigation
    implementation ("androidx.navigation:navigation-compose:2.5.3")

    // Dagger hilt
    implementation ("com.google.dagger:hilt-android:2.44.2")
    kapt ("com.google.dagger:hilt-compiler:2.44.2")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    // appwrite
    implementation("io.appwrite:sdk-for-android:1.2.1")

    // gson
    implementation ("com.google.code.gson:gson:2.9.1")

    // coil
    implementation("io.coil-kt:coil-compose:2.4.0")
    
    // date picker
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")

    testImplementation ("junit:junit:4.13.2")

    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.4.3")

    debugImplementation ("androidx.compose.ui:ui-tooling:1.4.3")

}