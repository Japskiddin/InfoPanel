package io.github.japskiddin.android.core.buildlogic

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(dependency: Any) {
    add("implementation", dependency)
}

fun DependencyHandlerScope.api(dependency: Any) {
    add("api", dependency)
}

fun DependencyHandlerScope.detektPlugins(provider: Any) {
    add("detektPlugins", provider)
}

fun DependencyHandlerScope.androidTestImplementation(provider: Any) {
    add("androidTestImplementation", provider)
}

fun DependencyHandlerScope.testImplementation(provider: Any) {
    add("testImplementation", provider)
}
