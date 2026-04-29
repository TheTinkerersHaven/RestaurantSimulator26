plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jackson.databind)
}

application {
    mainClass.set("restaurantsim.main.Main") 
}