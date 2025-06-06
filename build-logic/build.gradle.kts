plugins {
    `java-gradle-plugin`
}

dependencies {
    implementation("io.papermc.paperweight.userdev:io.papermc.paperweight.userdev.gradle.plugin:2.0.0-SNAPSHOT")
    implementation("net.fabricmc:access-widener:2.1.0")
    implementation("net.fabricmc:tiny-remapper:0.11.1:fat")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.zeroturnaround:zt-zip:1.17")
    implementation("com.google.guava:guava:33.0.0-jre")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

gradlePlugin {
    plugins {
        create("isletopia-awp") {
            id = "isletopia-awp"
            implementationClass = "com.molean.IgniteAWP"
        }
    }
}
