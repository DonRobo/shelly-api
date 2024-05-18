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
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.guava)
    implementation(libs.logback.classic)
    implementation(libs.jackson.kotlin)
    implementation(libs.bundles.ktor.client)
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            useKotlinTest("1.9.22")
        }
    }
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
            groupId = "io.github.shelly-api"
            artifactId = "shelly-api"
            version = project.version.toString()

            from(components["java"])
        }
    }
}
