@file:Suppress("UnstableApiUsage")

import java.util.Properties
import java.io.FileInputStream

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.hcapps.xpenzave"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.hcapps.xpenzave"
//        minSdk = 24
        minSdk = 26
        targetSdk = 33
        versionCode = 3
        versionName = "0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }

    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
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

    buildTypes.all { types ->
        val propertyFileName = if (types.name == "debug") "debug.remote.properties" else "release.remote.properties"
        val remotePropertiesFile = rootProject.file(propertyFileName)
        val remoteProperties = Properties()
        remoteProperties.load(FileInputStream(remotePropertiesFile))
        types.buildConfigField("String", "projectId", remoteProperties["projectId"] as String)
        types.buildConfigField("String", "databaseId", remoteProperties["databaseId"] as String)
        types.buildConfigField("String", "categoryCollectionId", remoteProperties["categoryCollectionId"] as String)
        types.buildConfigField("String", "budgetCollectionId", remoteProperties["budgetCollectionId"] as String)
        types.buildConfigField("String", "expenseCollectionId", remoteProperties["expenseCollectionId"] as String)
        types.buildConfigField("String", "photoBucketId", remoteProperties["photoBucketId"] as String)
        types.buildConfigField("String", "endpoint", remoteProperties["endpoint"] as String)
        types.buildConfigField ("java.util.HashMap<String, String>", "CATEGORIES", remoteProperties["categories"] as String)
        true // Apply configuration to all build types.
    }

}

dependencies {

    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.2")

    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-graphics")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.core:core-ktx:1.10.1")

    // lifecycle
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")


    // material icons
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")

    // timber
    implementation ("com.jakewharton.timber:timber:5.0.1")

    // navigation
    implementation ("androidx.navigation:navigation-compose:2.6.0")

    // Dagger hilt
    implementation ("com.google.dagger:hilt-android:2.44.2")
    kapt ("com.google.dagger:hilt-compiler:2.44.2")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    // appwrite
    implementation("io.appwrite:sdk-for-android:2.0.0")

    // gson
    implementation ("com.google.code.gson:gson:2.10.1")

    // coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation ("androidx.compose.animation:animation:1.4.3")
    implementation ("androidx.compose.animation:animation-graphics:1.4.3")
    
    // date picker
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")

    // flow layout
    implementation ("com.google.accompanist:accompanist-flowlayout:0.30.1")

    // room db
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")
    ksp ("androidx.room:room-compiler:2.5.2")

    // splash
    implementation ("androidx.core:core-splashscreen:1.0.1")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.firebase:firebase-analytics-ktx:21.3.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.4.0")


    testImplementation ("junit:junit:4.13.2")

    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.4.3")

    debugImplementation ("androidx.compose.ui:ui-tooling:1.4.3")

}