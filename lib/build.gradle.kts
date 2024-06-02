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
    repositories {
        val gprUser = (project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER"))?.ifBlank { null }
        if (gprUser != null) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/DonRobo/shelly-api")
                credentials {
                    username = gprUser
                    password =
                        (project.findProperty("gpr.key") as String? ?: System.getenv("GPR_TOKEN"))?.ifBlank { null }
                            ?: error("No GitHub token set")
                }
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            val ghRef = System.getenv("GH_REF")
            val versionToUse = when {
                ghRef == null -> project.version.toString()
                ghRef.startsWith("refs/heads/") -> ghRef.removePrefix("refs/heads/") + "-SNAPSHOT"
                ghRef.startsWith("refs/tags/") -> ghRef.removePrefix("refs/tags/")
                else -> error("Unknown GH_REF: $ghRef")
            }
            println("versionToUse: $versionToUse")
            groupId = "at.robert.shelly-api"
            artifactId = "shelly-api"
            version = versionToUse

            from(components["java"])
        }
    }
}
