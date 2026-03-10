plugins {
    kotlin("multiplatform") version "2.1.20"
}

repositories {
    mavenCentral()
    google()
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "app.js"
            }
        }
    }

    sourceSets {
        val jsMain by getting
    }
}
