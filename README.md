# PokeSpectra


## Introduction
PokeSpectra Android app consumes the free PokeAPI. PokeAPI is a free and open RESTful API that provides access to a vast amount of Pokémon-related data. 
It includes information about Pokémon species, abilities, types, evolutions, and much more used to create applications related to the Pokémon franchise.
This app is a simple demonstration of the PokeAPI.

## Requirements to setup
- Android Studio with JDK 17
- Android Gradle Plugin 8.1+
- Kotlin 1.8+

## How to setup

- Clone the repository
- Build the project (Disable Gradle offline if yo need to sync the dependencies)


## PokeSpectra Tech stack

This project has been implemented using a combination of tools and resources, some of which are;
* Architecture of choice: MVVM - Model View View Model
* UI - Jetpack Compose
* CI/CD - Continuous Integration and Continuous Delivery: Github Actions

* Dependencies + Third party libraries
    * [Kotlin](https://kotlinlang.org) - Kotlin is a cross-platform, statically typed, general-purpose high-level programming language with type inference
    * [Android Jetpack](https://developer.android.com/jetpack/getting-started) - Jetpack encompasses a collection of Android libraries that incorporate best practices and provide backwards compatibility in your Android apps.
    * [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Provides concept of suspending function providing a safer and less error-prone abstraction for asynchronous operations
    * [Dagger Hilt](https://dagger.dev/hilt/) - Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
    * [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and the JVM
    * [Firebase crashlytics](https://firebase.google.com/docs/crashlytics) Helps you track, prioritize, and fix stability issues that erode your app quality.
    * [AndroidX Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
    * [OKHTTP](https://square.github.io/okhttp/) - OkHttp is an HTTP client
    * [Logging Interceptor](https://square.github.io/okhttp/features/interceptors/) - Interceptors are a powerful mechanism that can monitor, rewrite, and retry calls
    * [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
    * [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API which provides utility on top of Android's normal Log class.
    
* Unit testing - A unit test verifies the behavior of a small section of code, the unit under test.
    * [Robolectric](https://robolectric.org) - Robolectric is a framework that brings fast and reliable unit tests to Android
    * [MockK](https://mockk.io) - Supports regular unit tests allowing you to mock objects.
    * [Google Truth](https://github.com/google/truth) - Fluent assertions for Java and Android

  

# Screenshots

<p align="center">
<img width="40%" alt="Screenshot 2023-12-21 at 16 57 53" src="https://github.com/mcobunga/pokespectra/assets/25502580/dcd7e3e4-0e11-40c9-b469-b9e68094b0f9" style="centre">
</p>


<p align="center">
<img width="40%" alt="Screenshot 2023-12-21 at 11.02.42" src="https://github.com/mcobunga/pokespectra/assets/25502580/350bd941-1fcb-4713-a308-b20bee455df2" style="centre">
</p>


<p align="center">
<img width="40%" alt="Screenshot 2023-12-21 at 17 02 14" src="https://github.com/mcobunga/pokespectra/assets/25502580/446bb309-eba0-47fd-ac79-fc8ee58bc17e" style="centre">
</p>











