plugins {
    application
}

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.release.set(21)
}

application {
    mainClass.set("com.example.dashboard.App")
}