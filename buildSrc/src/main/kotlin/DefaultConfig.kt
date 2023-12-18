import org.gradle.api.JavaVersion
object DefaultConfig {
    const val namespace = "com.bonface.pokespectra"
    const val applicationId = "com.bonface.pokespectra"
    const val compileSdkVersion = 34
    const val minSdkVersion = 24
    const val targetSdkVersion = compileSdkVersion
    const val versionCode = 1
    const val versionName = "1.0"
    const val jvmTarget = "1.8"
    const val kotlinCompilerExtensionVersion = "1.5.5"
    const val appNameRelease = "PokeSpectra"
    const val appNameStaging = "PokeSpectraBeta"
    const val appNameDebug = "PokeSpectraDebug"

    const val buildTools = "28.0.3"
    val stagingResConfigs = arrayOf("en", "en-rUS", "xxhdpi")
    val kotlinCompilerArgs = arrayOf("-Xjvm-default=all")
    val jvmVersion = JavaVersion.VERSION_17
}
