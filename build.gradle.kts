// Top-level build file
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.navigationSafeArgs) apply false
 //   alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.ksp) apply false
}


buildscript {
    repositories {
        google()
        mavenCentral()
    }
}