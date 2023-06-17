//classpath 'com.android.tools.build:gradle:4.0.1'

plugins {
    id("com.android.application") version "7.4.1" apply false
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44.2" apply false
    id("com.google.protobuf") version "0.8.19" apply false

    kotlin("jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
}
