plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.abdullah.composeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.abdullah.composeapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Dependencies.androidxCoreKtx)
    implementation(Dependencies.androidxActivityCompose)
    implementation(Dependencies.androidxMaterial3)
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.converterGson)
    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.kotlinxCoroutinesAndroid)
    implementation(Dependencies.loggingInterceptor)
    implementation(Dependencies.lottieCompose)
    implementation(Dependencies.timber)
    implementation(Dependencies.androidxLifecycleRuntimeKtx)
    implementation(Dependencies.androidxLifecycleViewmodelKtx)
    implementation(Dependencies.androidxNavigationCompose)
    implementation(Dependencies.androidxHiltNavigationCompose)
    implementation(Dependencies.androidxRuntimeLivedata)
    implementation(Dependencies.androidxUi)
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpUrlconnection)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.uiToolingPreview)
    implementation(Dependencies.androidxConstraintlayoutCompose)
    implementation(Dependencies.androidxEmoji2)
    implementation(Dependencies.androidxLifecycleRuntimeCompose)
    implementation(Dependencies.androidxLifecycleViewmodelCompose)
    implementation(Dependencies.androidxMaterial)

    testImplementation(Dependencies.junit)

    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.androidxEspressoCore)
    androidTestImplementation(Dependencies.androidxUiTestJunit4)

    debugImplementation(Dependencies.androidxUiTestManifest)
    debugImplementation(Dependencies.androidxUiTooling)

    kapt(Dependencies.hiltAndroidCompiler)
}



