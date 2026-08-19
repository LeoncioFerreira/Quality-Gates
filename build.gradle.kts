import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    kotlin("jvm") version "2.3.21"
    application
    jacoco
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("org.jlleitschuh.gradle.ktlint") version "14.2.0"
    id("org.sonarqube") version "7.3.1.8318"
}

application {
    mainClass.set("sistema.alunos.MainKt")
}

group = "sistema.alunos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.named("jacocoTestReport"))
}

jacoco {
    toolVersion = "0.8.14"
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
}

tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(tasks.named("jacocoTestReport"))

    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    parallel = true
    ignoreFailures = false
    basePath = rootProject.projectDir.absolutePath
    baseline = file("config/detekt/baseline.xml")
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        sarif.required.set(true)
        md.required.set(false)
    }
}

ktlint {
    ignoreFailures.set(false)
    outputToConsole.set(true)
    baseline.set(file("config/ktlint/baseline.xml"))
}

sonar {
    properties {
        property("sonar.projectName", "Quality Gates Kotlin")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.sources", "src/main")
        property("sonar.tests", "src/test")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.qualitygate.wait", "true")

        System.getenv("SONAR_PROJECT_KEY")?.let { property("sonar.projectKey", it) }
        System.getenv("SONAR_ORGANIZATION")?.let { property("sonar.organization", it) }
    }
}

tasks.named("sonar") {
    dependsOn(tasks.named("jacocoTestReport"))
}

tasks.named("check") {
    dependsOn(
        tasks.named("jacocoTestCoverageVerification"),
        tasks.named("detekt"),
        tasks.named("ktlintCheck"),
    )
}

tasks.register("qualityGate") {
    group = "verification"
    description = "Executa build, testes, cobertura, analise estatica e estilo."
    dependsOn(tasks.named("build"))
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    jvmArgs("-Dfile.encoding=UTF-8")
}
