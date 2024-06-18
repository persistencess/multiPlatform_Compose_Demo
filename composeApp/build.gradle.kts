import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
//     json注解
    kotlin("plugin.serialization") version "2.0.0"

    id("app.cash.sqldelight") version "2.0.2"
}
sqldelight{
    databases{
        // 自动生成数据库类名
        create("DatabaseSchema"){
            // 配置包名
            packageName.set("example.project.commonMain.cache")
        }
    }
}
kotlin {
  /* @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                    }
                }
            }
        }
        binaries.executable()
    }*/

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting
        val voyagerVersion = "1.1.0-beta02"

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            // 网络请求驱动
            implementation(libs.ktorClientAndroid)
            implementation(libs.ktorClientCio)
            // sql android驱动
            implementation("app.cash.sqldelight:android-driver:2.0.2")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            // 字体配置
            implementation(compose.components.resources)
            // 加载网络图片
            implementation(libs.kamelImage)
            // 网络请求
            implementation(libs.ktorCore)
            implementation(libs.ktorContentNegotiation)
            implementation(libs.ktorSerialization)
            implementation(libs.ktorClientLogging)

            // navigator
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
            // Screen Model
            implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
            // transition
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

            // sql android驱动

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            // 网络请求驱动
            implementation(libs.ktorClientCio)

            implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
        }
        iosMain.dependencies {
            // 网络请求驱动
            implementation(libs.ktorClientDarwin)

            implementation("app.cash.sqldelight:native-driver:2.0.2")
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}

// 禁用所有测试任务
tasks.withType<Test>().configureEach {
    enabled = false
}