plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.travelapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.travelapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")

//    implementation("com.google.android.material:material:1.8.0")
    implementation("com.google.firebase:firebase-firestore-ktx:23.0.0")
//    implementation("com.google.android.gms:play-services-tasks:19.8.0")

    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.play.services.tasks)
    implementation(libs.firebase.auth.ktx)
    kapt("androidx.room:room-compiler:2.6.1")
//    implementation(libs.firebase.firestore.ktx)
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.firebaseui:firebase-ui-auth:8.0.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}