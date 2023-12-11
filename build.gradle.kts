plugins {
    kotlin("jvm") version "1.9.20"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

dependencies {
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
