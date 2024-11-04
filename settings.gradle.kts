import java.net.URI

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven {
            url = URI.create("https://artifact.bytedance.com/repository/pangle")
        }
        maven {
            url = URI.create("https://android-sdk.is.com/")
        }
        maven {
            url =
                URI.create("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea")
        }

        val username = providers.gradleProperty("artifactory_username").get()
        val password = providers.gradleProperty("artifactory_password").get()

        maven {
            url = URI.create("http://139.59.246.166:8082/artifactory/tapi-library")
            isAllowInsecureProtocol = true
            credentials {
                this.username = username
                this.password = password
            }
        }

        maven {
            url = URI.create("http://139.59.246.166:8082/artifactory/vincent-library")
            isAllowInsecureProtocol = true
            credentials {
                this.username = username
                this.password = password
            }
        }
    }
}

rootProject.name = "Lime Player"
include(":app")

include(":local-file-manager")

include(":catcher")
include(":manager")
include(":downloader")
include(":ffmpeg-core")
project(":catcher").projectDir = File("../0038.AllDownloaderCatcher/catcher")
project(":manager").projectDir = File("../0038.AllDownloadCore/manager")
project(":downloader").projectDir = File("../0038.AllDownloadCore/downloader")
project(":ffmpeg-core").projectDir = File("../0038.AllDownloadCore/ffmpeg-core")
 