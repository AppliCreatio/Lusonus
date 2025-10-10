import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("com.android.application") version "8.6.1" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
    filter {
        exclude("**/style-violations.kt")
    }
}
