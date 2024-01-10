plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    // Google map plugin
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

    // Kapt and dagger hilt
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.pawsome"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pawsome"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        // Date-time picker
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("androidx.camera:camera-camera2:1.0.1")
    implementation("androidx.camera:camera-lifecycle:1.0.1")
    implementation ("androidx.camera:camera-view:1.0.0-alpha27")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha10")
    implementation("androidx.compose.material:material-icons-core:1.0.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha10")
    implementation("androidx.compose.material:material-icons-core:1.0.1") // Use the latest version
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("androidx.navigation:navigation-compose:2.4.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("io.coil-kt:coil:2.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.compose.material:material:1.1.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    /* ---- Firebase ---- */

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")

    // Add the dependency for the Realtime Database library
    implementation("com.google.firebase:firebase-database")

    implementation("com.google.firebase:firebase-common-ktx:20.4.2")

    // Add the dependency for the Firestore library
    implementation("com.google.firebase:firebase-firestore:24.10.0")

    // Add the dependency for the Storage
    implementation("com.google.firebase:firebase-storage:20.2.0")

    /* ---- Google Maps ---- */

    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.maps.android:maps-compose:4.1.1")
    implementation("com.google.maps.android:maps-utils-ktx:3.2.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.0.0-beta01")

    // Permission
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")

    /* ---- Testing ---- */

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    /* ---- Navigation control ---- */

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Dagger hilt and kapt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // core libary
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.6")

    // Stream Chat
    implementation("io.getstream:stream-chat-android-compose:6.0.8")
    implementation("io.getstream:stream-chat-android-offline:6.0.8")

    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")

    implementation("com.stripe:stripe-android:20.36.0")
    // Stripe API
}