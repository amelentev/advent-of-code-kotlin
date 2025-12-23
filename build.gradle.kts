plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ksmt:ksmt-core:0.6.4")
    implementation("io.ksmt:ksmt-z3:0.6.4")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "8.5"
    }
}
