plugins {
    kotlin("multiplatform") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
    id("maven-publish")
}

group = "de.twiese99"
version = "1.1.0"

repositories {
    mavenCentral()
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("AxiosKt")
            description.set("Kotlin Wrapper for Axios HTTP Client")
            url.set("https://github.com/twiese99/axioskt")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://github.com/twiese99/axioskt/blob/main/LICENSE")
                }
            }
            scm {
                url.set("https://github.com/twiese99/axioskt")
            }
        }
    }
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
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(npm("axios", "0.22.0"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
