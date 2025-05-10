plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    api(kotlin("gradle-plugin:1.9.0"))
    implementation("com.android.tools.build:gradle-api:8.1.4")
}