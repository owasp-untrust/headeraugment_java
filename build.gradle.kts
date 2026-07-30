plugins {
    `java-library`
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.owasp.untrust"
version = "0.1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.6")
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework:spring-webmvc")
    compileOnly("jakarta.servlet:jakarta.servlet-api")
}
