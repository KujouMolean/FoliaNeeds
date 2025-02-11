import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("com.molean.ignite.access-widener") version "1.0"
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "com.molean"
version = "1.0-SNAPSHOT"
paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
repositories {
    mavenCentral()
    // mixin
    maven {
        url = uri("https://repo.spongepowered.org/maven/")
    }
    mavenLocal()
}

dependencies {
    paperweight.foliaDevBundle("1.21.4-R0.1-SNAPSHOT")

    compileOnly("space.vectrix.ignite:ignite-api:1.0.1")
    compileOnly("org.spongepowered:mixin:0.8.5")
    compileOnly("io.github.llamalad7:mixinextras-common:0.3.5")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.jetbrains:annotations:23.0.0")

//    shadow("com.molean:FoliaAdapter:1.0-SNAPSHOT:dev")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<ProcessResources>("processResources") {
    inputs.property("version", project.version)
    filteringCharset = "UTF-8"
    filesMatching("ignite.mod.json") {
        expand(mapOf("version" to project.version))
    }
}

tasks.named("compileJava") {
    dependsOn(tasks.named("applyAccessWidener"))
}

tasks.named<ShadowJar>("shadowJar") {
    minimize {
        exclude(dependency("com.molean:.*:.*"))
    }
    configurations = listOf(project.configurations.getByName("shadow"))
    mergeServiceFiles()
}