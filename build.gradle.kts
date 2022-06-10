buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.android.tools.build:gradle:7.2.0")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.6.21")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}