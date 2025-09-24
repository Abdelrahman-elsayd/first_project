plugins {
    id("io.micronaut.application") version "4.5.4"
    id("com.gradleup.shadow") version "8.3.7"
    id("io.micronaut.aot") version "4.5.4"
    id("jacoco")
    id("org.sonarqube") version "5.1.0.4882"
}

version = "0.1"
group = "com.example"

repositories {
    mavenCentral()
}

dependencies {
    // === Lombok (Builder + Data) ===
    implementation("org.projectlombok:lombok:1.18.30")
    compileOnly("org.projectlombok:lombok:1.18.30")          // for main source
    annotationProcessor("org.projectlombok:lombok:1.18.30")   // enable code generation

    testCompileOnly("org.projectlombok:lombok:1.18.30")       // for tests
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    // --- Micronaut ---
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")

    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("com.h2database:h2")

    implementation("io.micronaut.serde:micronaut-serde-jackson")
    runtimeOnly("ch.qos.logback:logback-classic")

    compileOnly("io.micronaut:micronaut-http-client")
    testImplementation("io.micronaut:micronaut-http-client")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    // Kafka
    implementation("io.micronaut.kafka:micronaut-kafka")

    // Reactor (Reactive Programming)
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.projectreactor:reactor-core")

    // Native Image (GraalVM) – optional
    // implementation("io.micronaut.graalvm:micronaut-graalvm")
    implementation("io.micronaut.jms:micronaut-jms-activemq-classic")  // JMS support
    implementation("org.apache.activemq:activemq-broker:5.18.3")       // Embedded ActiveMQ broker


    testImplementation("org.awaitility:awaitility:4.2.2")


    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.jms:micronaut-jms-activemq-classic")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("org.slf4j:slf4j-api:2.0.16")



}

application {
    mainClass = "com.example.Application"
}

java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental.set(true)
        annotations("com.example.*")
    }
    aot {
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
        replaceLogbackXml.set(true)
    }
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}

jacoco {
    toolVersion = "0.8.10"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

sonar {
    properties {
        property("sonar.projectKey", "com.example:customer-api")
        property("sonar.projectName", "Micronaut Customer API")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.login", "sqa_c67dae061af5cadb87dcde18416171e185679e9b")
        property("sonar.java.binaries", "build/classes")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}
