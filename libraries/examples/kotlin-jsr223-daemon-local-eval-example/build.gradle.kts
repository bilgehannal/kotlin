
description = "Sample Kotlin JSR 223 scripting jar with daemon (out-of-process) compilation and local (in-process) evaluation"

apply { plugin("kotlin") }

dependencies {
    val compile by configurations
    val testCompile by configurations
    val testRuntime by configurations
    compile(project(":kotlin-stdlib"))
    compile(project(":kotlin-script-runtime"))
    compile(projectRuntimeJar(":kotlin-compiler"))
    compile(project(":kotlin-script-util"))
    compile(projectRuntimeJar(":kotlin-daemon-client"))
    testCompile(project(":kotlin-test:kotlin-test-junit"))
    testRuntime(project(":kotlin-reflect"))
}

tasks.withType<Test> {
    jvmArgs("-ea", "-XX:+HeapDumpOnOutOfMemoryError", "-Xmx1200m", "-XX:+UseCodeCacheFlushing", "-XX:ReservedCodeCacheSize=128m", "-Djna.nosys=true")
    maxHeapSize = "1200m"
    systemProperty("idea.is.unit.test", "true")
    environment("NO_FS_ROOTS_ACCESS_CHECK", "true")
    environment("KOTLIN_HOME", rootProject.extra["distKotlinHomeDir"])
    ignoreFailures = true
}