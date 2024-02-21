plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = DefaultConfig.namespace
    compileSdk = DefaultConfig.compileSdkVersion

    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.minSdkVersion
        targetSdk = DefaultConfig.targetSdkVersion
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = "pokespectra_${variant.baseName}_${variant.versionName}_${variant.versionCode}.apk"
                output.outputFileName = outputFileName
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = DefaultConfig.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions.unitTests.apply {
        isIncludeAndroidResources = true
        isReturnDefaultValues = true
    }
    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.features))
    implementation(Dependencies.coreKtx)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.material3)
    //Navigation
    implementation(Dependencies.composeNavigation)
    //Firebase crashlytics
    implementation(Dependencies.firebaseCrashlytics)
    implementation(Dependencies.firebaseAnalytics)
    //Splash Screen
    implementation(Dependencies.splash)
    //Dagger Hilt
    implementation(Dependencies.daggerHilt)
    ksp(Dependencies.daggerHiltKsp)
    //Timber
    implementation(Dependencies.timber)

    testImplementation(Dependencies.junit)
    implementation(Dependencies.junitKtx)
    androidTestImplementation(Dependencies.androidTestExt)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(platform(Dependencies.testComposeBom))
    androidTestImplementation(Dependencies.uiTestJunit4)
    debugImplementation(Dependencies.uiTooling)
    debugImplementation(Dependencies.uiTestManifest)
}
