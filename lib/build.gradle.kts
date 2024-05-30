plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.jvm)

    // Apply the java-library plugin for API and implementation separation.
    `java-library`

    `maven-publish`
}

version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.logback.classic)
    implementation(libs.jackson.kotlin)
    implementation(libs.bundles.ktor.client)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "at.robert.shelly-api"
            artifactId = "shelly-api"
            version = project.version.toString()

            from(components["java"])
        }
    }
}
