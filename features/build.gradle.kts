plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.bonface.pokespectra.features"
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = DefaultConfig.kotlinCompilerExtensionVersion
    }
}

dependencies {
    implementation(project(Modules.libs))
    implementation(project(Modules.core))
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiPreview)
    implementation(Dependencies.material3)
    implementation(Dependencies.materialGoogle)
    implementation(Dependencies.material)
    implementation(Dependencies.materialIcons)
    implementation(Dependencies.materialIconsExt)
    implementation(Dependencies.composeLifecycle)
    //Coroutines
    implementation(Dependencies.coroutineCore)
    implementation(Dependencies.coroutinesAndroid)
    // Coroutine Lifecycle Scopes
    implementation(Dependencies.lifecycleViewmodel)
    implementation(Dependencies.lifecycleRuntime)
    //Navigation
    implementation(Dependencies.composeNavigation)
    //Firebase crashlytics
    implementation(Dependencies.firebaseCrashlytics)
    implementation(Dependencies.firebaseAnalytics)
    implementation(Dependencies.hiltNavigation)
    //Splash Screen
    implementation(Dependencies.splash)
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
    //Coil for Image loading
    implementation(Dependencies.coilCompose)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.junitExt)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(platform(Dependencies.testComposeBom))
    androidTestImplementation(Dependencies.uiTestJunit4)
    debugImplementation(Dependencies.uiTooling)
    debugImplementation(Dependencies.uiTestManifest)
}