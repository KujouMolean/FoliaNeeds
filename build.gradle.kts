import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "2.0.0-SNAPSHOT"
    id("isletopia-awp")
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
    paperweight.foliaDevBundle("1.21.5-R0.1-SNAPSHOT")

    compileOnly("space.vectrix.ignite:ignite-api:1.0.1")
    compileOnly("org.spongepowered:mixin:0.8.5")
    compileOnly("io.github.llamalad7:mixinextras-common:0.3.5")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.jetbrains:annotations:23.0.0")
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

tasks.named<ShadowJar>("shadowJar") {
    minimize {
        exclude(dependency("com.molean:.*:.*"))
    }
    configurations = listOf(project.configurations.getByName("shadow"))
    mergeServiceFiles()
}



// 禁用自动的Userdev Setup
tasks.named("paperweightUserdevSetup").configure {
    enabled = false
}

gradle.taskGraph.whenReady {
    tasks.named("paperweightUserdevSetup").configure {
        enabled = project.hasProperty("setup")
    }
}


// 新建一个task只在手动执行时运行，确保AW之后的lib不会被覆盖
abstract class SetupTask : DefaultTask() {
    @Inject
    abstract fun getExecOperations(): ExecOperations

    init {
        // 设置任务组为 "build"
        group = "awp"
        description = "第一次构建需要手动初始化dev-bundle"

    }

    @TaskAction
    fun setup() {
        getExecOperations().exec {
            workingDir = project.projectDir
            executable = if (org.gradle.internal.os.OperatingSystem.current().isWindows) {
                "gradlew.bat"
            } else {
                "./gradlew"
            }
            args = listOf("paperweightUserdevSetup", "-Psetup")
        }
    }
}


tasks.register<SetupTask>("setupDevBundle")