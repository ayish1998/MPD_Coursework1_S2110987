plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.ayishatusaeedS2110987"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.coursework1"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures{
        viewBinding = true
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation ("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")


    implementation ("com.github.bumptech.glide:glide:4.12.0")

    //ViewPager2
    implementation ("androidx.viewpager2:viewpager2:1.0.0")


    implementation ("androidx.viewpager:viewpager:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")

}
