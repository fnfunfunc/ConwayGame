plugins {
    kotlin("multiplatform") version "1.8.20-RC2"
}

group = "me.eternal"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

@OptIn(org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl::class)
kotlin {
    wasm {
        binaries.executable()
        browser()
    }


    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val wasmMain by getting
        val wasmTest by getting
    }
}
