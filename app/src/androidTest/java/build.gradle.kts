apply plugin: 'com.android.application'

android {
    compileSdkVersion 30

    defaultConfig {
        applicationId "com.example.team12.test" // Thay thế bằng tên gói của bạn
        minSdkVersion 16
        targetSdkVersion 30
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation project(':app') // Thay thế bằng tên module của ứng dụng chính
    androidTestImplementation 'androidx.test:runner:1.4.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
