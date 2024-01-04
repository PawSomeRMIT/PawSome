buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")

        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false

    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.0" apply false

    id("com.android.library") version "7.3.1" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}