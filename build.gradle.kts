plugins {
    id("java-library")
    id("maven-publish")
}

group = "net.chamosmp.modl.minecraft.replay"
version = "1.1.8"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
    maven("https://nexus.modl.gg/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-releases/")
}

dependencies {
    api(libs.replay.format)
    implementation(libs.gson)

    compileOnly(libs.packetevents.api)

    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.gson)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation(libs.packetevents.api)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.launcher)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "ChamoSMP-Releases"
            url = uri("https://maven.chamosmp.net/releases")
            credentials {
                username = System.getenv("REPOSILITE_USER")
                password = System.getenv("REPOSILITE_TOKEN")
            }
        }
        maven {
            name = "ChamoSMP-Snapshots"
            url = uri("https://maven.chamosmp.net/snapshots")
            credentials {
                username = System.getenv("REPOSILITE_USER")
                password = System.getenv("REPOSILITE_TOKEN")
            }
        }
    }
}