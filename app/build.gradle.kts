plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.app_recetas"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.app_recetas"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.7.7")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // Room Database (Para almacenamiento local)
    implementation("androidx.room:room-runtime:2.6.1") // ✅ Versión compatible
    implementation("androidx.room:room-ktx:2.6.1") // ✅ Extensiones KTX
    kapt("androidx.room:room-compiler:2.6.1") // ✅ `kapt` compatible con Kotlin 2.0
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("androidx.activity:activity-ktx:1.8.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // DataStore (Para manejo de sesión)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Glide para imágenes
    implementation("com.github.bumptech.glide:glide:4.12.0")


    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}