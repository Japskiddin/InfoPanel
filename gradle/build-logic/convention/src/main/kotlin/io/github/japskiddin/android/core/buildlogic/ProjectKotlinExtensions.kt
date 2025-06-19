package io.github.japskiddin.android.core.buildlogic

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal fun Project.configureKotlinAndroid(
    androidExtension: AndroidExtension
) {
    androidExtension.apply {
        compileSdk = libs.versions.android.compileSdk.get().toString().toInt()

        defaultConfig {
            minSdk = libs.versions.android.minSdk.get().toString().toInt()
        }

        compileOptions {
            sourceCompatibility = projectJavaVersion
            targetCompatibility = projectJavaVersion
        }
    }
}

internal fun Project.configureKotlinJvm() {
    kotlinJvmCompilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
    }
}
