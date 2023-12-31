import org.gradle.api.JavaVersion

object DefaultConfig {
    const val namespace = "com.bonface.pokespectra"
    const val applicationId = "com.bonface.pokespectra"
    const val compileSdkVersion = 34
    const val minSdkVersion = 24
    const val targetSdkVersion = compileSdkVersion
    const val versionCode = 1
    const val versionName = "1.0"
    const val kotlinCompilerExtensionVersion = "1.5.5"

    val jvmVersion = JavaVersion.VERSION_17
}
