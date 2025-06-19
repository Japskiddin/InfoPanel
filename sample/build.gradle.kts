plugins {
    alias(libs.plugins.app.android.application)
    alias(libs.plugins.app.detekt)
    alias(libs.plugins.app.test)
}

android {
    namespace = "io.github.japskiddin.infopanel.sample"

    defaultConfig {
        applicationId = "io.github.japskiddin.infopanel.sample"
        versionCode = 1
        versionName = "1.0.0"
    }

    androidResources {
        @Suppress("UnstableApiUsage")
        generateLocaleConfig = true
    }
}

dependencies {
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(projects.library)
}
