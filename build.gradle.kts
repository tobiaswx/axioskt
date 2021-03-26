plugins {
    kotlin("js") version "1.4.31"
}

group = "de.twiese99"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(npm("axios", "0.21.1"))
    testImplementation(kotlin("test-js"))
}

kotlin {
    js(IR) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
}