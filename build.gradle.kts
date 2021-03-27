plugins {
    kotlin("js") version "1.4.31"
    id("maven-publish")
}

group = "de.twiese99"
version = "1.0.0"

repositories {
    mavenCentral()
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("AxiosKt")
            description.set("Kotlin Wrapper for Axios HTTP Client")
            url.set("https://github.com/twiese99/AxiosKt")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://github.com/twiese99/AxiosKt/blob/main/LICENSE")
                }
            }
            scm {
                url.set("https://github.com/twiese99/AxiosKt")
            }
        }
    }
}

dependencies {
    implementation(npm("axios", "0.21.1"))
    testImplementation(kotlin("test-js"))
}

kotlin {
    js(BOTH) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
}