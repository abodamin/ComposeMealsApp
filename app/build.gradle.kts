plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.DAGGER_HILT)
    id(BuildPlugins.KOTLIN_PARCELIZE)
}

android {
    namespace = BuildConfig.APP_ID
    compileSdk = BuildConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = BuildConfig.APP_ID
        minSdk = BuildConfig.MIN_SDK_VERSION
        targetSdk = BuildConfig.TARGET_SDK_VERSION
        versionCode = ReleaseConfig.VERSION_CODE
        versionName = ReleaseConfig.VERSION_NAME

        testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions.add(BuildDimensions.APP)
    flavorDimensions.add(BuildDimensions.STORE)

    productFlavors {
        BuildFlavor.Google.create(this)
        BuildFlavor.Huawei.create(this)
        BuildFlavor.Client.create(this)
        BuildFlavor.Driver.create(this)
    }

    buildTypes {
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = Build.Release.isMinifyEnabled
            enableUnitTestCoverage = Build.Release.enableUnitTestCoverage
            isDebuggable = Build.Release.isDebuggable
            versionNameSuffix = Build.Release.versionNameSuffix
            applicationIdSuffix = Build.Release.applicationIdSuffix
            signingConfig = signingConfigs.getByName(SigningTypes.RELEASE)
        }

        getByName(BuildTypes.DEBUG){
            isMinifyEnabled = Build.Debug.isMinifyEnabled
            enableUnitTestCoverage = Build.Debug.enableUnitTestCoverage
            isDebuggable = Build.Debug.isDebuggable
            versionNameSuffix = Build.Debug.versionNameSuffix
            applicationIdSuffix = Build.Debug.applicationIdSuffix
            signingConfig = signingConfigs.getByName(SigningTypes.DEBUG)
        }

        create(BuildTypes.RELEASE_EXTERNAL_QA) {
            isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
            enableUnitTestCoverage = Build.ReleaseExternalQa.enableUnitTestCoverage
            isDebuggable = Build.ReleaseExternalQa.isDebuggable
            versionNameSuffix = Build.ReleaseExternalQa.versionNameSuffix
            applicationIdSuffix = Build.ReleaseExternalQa.applicationIdSuffix
            signingConfig = signingConfigs.getByName(SigningTypes.RELEASE_EXTERNAL_QA)
        }
    }

    signingConfigs {
        BuildSigning.Release.create(this)
        BuildSigning.ReleaseExternalQa.create(this)
        BuildSigning.Debug.create(this)
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



