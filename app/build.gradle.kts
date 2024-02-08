plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.relay") version "0.3.09"
}

android {
    namespace = "com.example.mago"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mago"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        //manifestPlaceholders == [ auth0Domain: "@string/com_auth0_domain", auth0Scheme: "@string/com_auth0_scheme" ]
        manifestPlaceholders["auth0Domain"] = "@string/com_auth0_domain"
        manifestPlaceholders["auth0Scheme"] = "@string/com_auth0_scheme"



        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        //manifestPlaceholders["hostName"] = "com.example.mago"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.foundation:foundation-android:1.5.4")
    implementation("androidx.navigation:navigation-common-ktx:2.7.5")
    implementation("androidx.wear.compose:compose-material:1.2.1")
    implementation("androidx.wear.compose:compose-material3:1.0.0-alpha15")
    implementation("com.google.android.engage:engage-core:1.3.1")
    implementation("org.jetbrains:annotations:15.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.paging:paging-common-android:3.3.0-alpha02")
    implementation("com.google.android.gms:play-services-fido:20.1.0")
    implementation("androidx.browser:browser:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation ("androidx.navigation:navigation-compose:2.7.5")
    implementation ("androidx.compose.ui:ui:1.5.4")



    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")
    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha02")
    implementation ("io.coil-kt:coil-compose:1.4.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.0.5")
    implementation ("androidx.compose.runtime:runtime:1.0.5")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("com.squareup.moshi:moshi:1.12.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.6.2")


    //implementation ("androidx.compose.material:material")

    implementation("androidx.compose.material3:material3-android:1.2.0-alpha10")

   //implementation ("com.auth0.android:auth0:2.10.2")
    implementation ("com.auth0.android:auth0:2.9.2")
    implementation ("com.auth0.android:jwtdecode:2.0.0")

    implementation ("com.auth0.android:auth0:2.9.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation ("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation ("io.jsonwebtoken:jjwt-jackson:0.11.2")

    implementation("co.yml:ycharts:2.1.0")


}