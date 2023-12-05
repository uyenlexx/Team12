plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.team12"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.team12"
        minSdk = 24
        targetSdk = 33
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.firebaseui:firebase-ui-database:8.0.2")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("androidx.activity:activity-ktx:1.2.3")
    implementation ("androidx.fragment:fragment-ktx:1.3.3")
    implementation ("androidx.test:rules:1.5.0")

    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    //define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")

    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("androidx.test.espresso:espresso-contrib:3.5.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    testImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("com.google.truth:truth:1.1.3")

    // for API usage
    implementation("com.squareup.okhttp3:okhttp")
}