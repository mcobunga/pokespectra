
object Versions {
    const val coreKtx = "1.12.0"
    const val lifecycleRuntimeKtx = "2.6.2"
    const val activityCompose = "1.8.2"
    const val composeBom = "2023.08.00"
    const val retrofit = "2.9.0"
    const val firebaseCrashlytics = "18.6.0"
    const val firebaseAnalytics = "21.5.0"
    const val splash = "1.1.0-alpha02"
    const val daggerHilt = "2.48.1"
    const val okhttp = "5.0.0-alpha.2"
    const val loggingInterceptor = "5.0.0-alpha.6"
    const val moshi = "1.15.0"
    const val moshiConverter = "2.9.0"
    const val timber = "5.0.1"

    const val junit = "4.13.2"
    const val junitExt = "1.1.5"
    const val espressoCore = "3.5.1"
    const val testComposeBom = "2023.08.00"
    // Add other dependency versions
}

object Dependencies {

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"

    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiPreview = "androidx.compose.ui:ui-tooling-preview"
    const val material3 = "androidx.compose.material3:material3"
    //Firebase crashlytics
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:${Versions.firebaseCrashlytics}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics:${Versions.firebaseAnalytics}"
    //Splash Screen
    const val splash = "androidx.core:core-splashscreen:${Versions.splash}"
    //Dagger Hilt
    const val daggerHilt= "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val daggerHiltKsp = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    //Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    //OKHTTP
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    //Logging Interceptor
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    //Moshi
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiKsp = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.moshiConverter}"

    //Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    
    
    
    
    
    
    
    
    
    
    
    
    const val junit = "junit:junit:${Versions.junit}"
    const val junitExt = "androidx.test.ext:${Versions.junitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val testComposeBom = "androidx.compose:compose-bom:${Versions.testComposeBom}"
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4"
    const val uiTooling = "androidx.compose.ui:ui-tooling"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"


}
