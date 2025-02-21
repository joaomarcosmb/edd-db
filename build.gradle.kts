plugins {
    id("java")
    id("application")
}

group = "org.edd"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.edd.Main")
}

repositories {
    mavenCentral()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.edd.Main"
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}