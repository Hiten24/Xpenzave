buildscript {
    val agp_version by extra("8.2.1")
    val agp_version1 by extra("8.1.0")
}
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.google.firebase.crashlytics") version "2.9.7" apply false
}