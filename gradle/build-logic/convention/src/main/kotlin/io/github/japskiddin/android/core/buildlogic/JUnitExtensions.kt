package io.github.japskiddin.android.core.buildlogic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureJUnitAndroid() {
    dependencies {
        androidTestImplementation(libs.androidx.test.espresso.core)
        androidTestImplementation(libs.androidx.test.ext.junit)
        androidTestImplementation(libs.androidx.arch.core.testing)
        androidTestImplementation(libs.google.truth)
        androidTestImplementation(libs.kotlinx.coroutines.test)
    }
}

fun Project.configureJUnit() {
    dependencies {
        testImplementation(libs.junit)
        testImplementation(libs.kotlinx.coroutines.test)
    }
}
