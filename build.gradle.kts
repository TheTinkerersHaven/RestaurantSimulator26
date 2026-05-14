plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jackson.databind)
    implementation(libs.jlayer)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
    sourceCompatibility = JavaVersion.VERSION_1_6
    targetCompatibility = JavaVersion.VERSION_1_6
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:-options")
}

application {
    mainClass.set("restaurantsim.main.Main") 
}

tasks.register<Jar>("fatJar") {
    archiveBaseName.set("RestaurantSimulator")
    archiveAppendix.set("")
    archiveVersion.set("1.0")
    archiveClassifier.set("all")
    from(configurations.runtimeClasspath.map { config ->
        config.map { if (it.isDirectory) it else zipTree(it) }
    })
    with(tasks.jar.get())
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}
