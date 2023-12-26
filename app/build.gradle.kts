
plugins {
    id("com.android.application")
    id("kotlin-android")
    //id("kotlin-kapt")
}

android {
    namespace = "com.detox"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.detox"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        
    }
    
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}
configurations.all {
    resolutionStrategy {
        force("org.xerial:sqlite-jdbc:3.40.1.0")
    }
}
dependencies {


    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.22")
    
    /*implementation("androidx.recyclerview:recyclerview-selection:1.1.0")

    var room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    //ksp "androidx.room:room-compiler:$room_version"

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$room_version")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    var lifecycle_version = "2.6.2"
    var arch_version = "2.2.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime:$lifecycle_version")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")

    // Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")

    // optional - helpers for implementing LifecycleOwner in a Service
    implementation("androidx.lifecycle:lifecycle-service:$lifecycle_version")
    */
    implementation("com.github.bumptech.glide:glide:4.16.0")
}