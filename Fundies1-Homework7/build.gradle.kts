plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.dokka") version "1.9.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

apply(plugin = "org.jetbrains.dokka")
tasks.dokkaHtml.configure {
    outputDirectory.set(layout.buildDirectory.dir("dokka"))

    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(true)
        }
    }
}

application {
    mainClass.set("MondrianPainterKt")
}

// New task to run MyMondrianPainterKt
tasks.register<JavaExec>("runMyMondrianPainter") {
    group = "application"
    description = "Runs the MyMondrianPainterKt class"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("MyMondrianPainterKt")
}