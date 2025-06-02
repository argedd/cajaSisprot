import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)  // Usando KSP en lugar de kapt
}

android {
    namespace = "com.adminpay.caja"
    compileSdk = 35

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.adminpay.caja"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Cargando las variables del .env
        val envProps = Properties().apply {
            val envFile = rootProject.file(".env")
            if (envFile.exists()) {
                envFile.inputStream().use { load(it) }
            }
        }
        // Imprimir las propiedades en los logs de la compilación
        println("API_URL: ${envProps["API_URL"]}")

        buildConfigField("String", "API_URL", "\"${envProps["API_URL"]}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Material Icons
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // LiveData + Retrofit
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.retrofit)
    implementation(libs.androidx.retrofit.converter.gson)

    // Dagger Hilt con KSP
    implementation(libs.androidx.hilt.android)
    implementation(libs.androidx.benchmark.macro)
    ksp(libs.androidx.hilt.compiler)  // Procesamiento con KSP
    implementation(libs.androidx.hilt.navigation.compose)

    // ColorPicker
    implementation(libs.colorpicker.compose)
    implementation(libs.coil.compose)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    //lottie
    implementation (libs.lottie.compose)

    //Chars
    implementation (libs.compose.charts)




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}