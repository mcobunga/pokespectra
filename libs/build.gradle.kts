plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.bonface.pokespectra.libs"
    compileSdk = DefaultConfig.compileSdkVersion

    defaultConfig {
        minSdk = DefaultConfig.minSdkVersion
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
        sourceCompatibility = DefaultConfig.jvmVersion
        targetCompatibility = DefaultConfig.jvmVersion
    }
    kotlinOptions {
        jvmTarget = DefaultConfig.jvmVersion.toString()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    //Dagger Hilt
    implementation(Dependencies.daggerHilt)
    ksp(Dependencies.daggerHiltKsp)
    //Retrofit
    implementation(Dependencies.retrofit)
    //OKHTTP
    implementation(Dependencies.okhttp)
    //Logging Interceptor
    implementation(Dependencies.loggingInterceptor)
    //Moshi
    implementation(Dependencies.moshi)
    ksp(Dependencies.moshiKsp)
    implementation(Dependencies.moshiConverter)
    //Timber
    implementation(Dependencies.timber)
}