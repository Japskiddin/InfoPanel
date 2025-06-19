import com.android.build.api.dsl.ApplicationExtension
import io.github.japskiddin.android.core.buildlogic.androidConfig
import io.github.japskiddin.android.core.buildlogic.configureBuildTypes
import io.github.japskiddin.android.core.buildlogic.configureKotlinAndroid
import io.github.japskiddin.android.core.buildlogic.configureKotlinJvm
import io.github.japskiddin.android.core.buildlogic.libs
import io.github.japskiddin.android.core.buildlogic.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins {
                apply(libs.plugins.android.application.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
            }

            androidConfig {
                this as ApplicationExtension

                defaultConfig {
                    targetSdk = libs.versions.android.targetSdk.get().toString().toInt()
                    vectorDrawables { useSupportLibrary = true }
                    ndk {
                        abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
                    }
                }

                configureKotlinJvm()
                configureKotlinAndroid(this)
                configureBuildTypes()

                buildFeatures {
                    buildConfig = true
                    viewBinding = true
                }

                bundle {
                    language {
                        enableSplit = false
                    }
                }

                dependenciesInfo {
                    includeInApk = false
                    includeInBundle = false
                }
            }
        }
    }
}
