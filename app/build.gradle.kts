// プラグインの設定
plugins {
    alias(libs.plugins.android.application) // Androidアプリケーション用プラグイン
    alias(libs.plugins.kotlin.android) // Kotlin Android用プラグイン
}

android {
    namespace = "com.pi12a082.hf21" // アプリケーションのパッケージ名
    compileSdk = 34 // 使用するAPIレベル（SDKバージョン）

    defaultConfig {
        applicationId = "com.pi12a082.hf21" // アプリケーションID（ユニークな識別子）
        minSdk = 24 // サポートする最低SDKバージョン（Android 7.0）
        targetSdk = 34 // ターゲットとするSDKバージョン
        versionCode = 1 // アプリのバージョンコード（内部管理用）
        versionName = "1.0" // アプリのバージョン名（ユーザー表示用）

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // テスト実行時のランナー
    }

    buildTypes {
        release {
            isMinifyEnabled = false // リリースビルド時のコード圧縮を無効化
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), // デフォルトのProguard設定
                "proguard-rules.pro" // プロジェクト独自のProguard設定
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Java 1.8互換性を使用
        targetCompatibility = JavaVersion.VERSION_1_8 // Java 1.8互換性を使用
    }

    kotlinOptions {
        jvmTarget = "1.8" // KotlinのJVMターゲットをJava 1.8に設定
    }
}

dependencies {
    // AndroidXコアライブラリ
    implementation(libs.androidx.core.ktx) // コア機能の拡張（Kotlin）
    implementation(libs.androidx.appcompat) // アプリ互換性サポートライブラリ
    implementation(libs.material) // マテリアルデザインコンポーネント
    implementation(libs.androidx.activity) // アクティビティのライフサイクル管理
    implementation(libs.androidx.constraintlayout) // ConstraintLayoutのサポート

    // Google Maps SDK for Android
    implementation("com.google.android.gms:play-services-maps:18.1.0") // Googleマップの表示と操作用

    // 任意: ロケーションサービスが必要な場合
    implementation("com.google.android.gms:play-services-location:21.0.1") // 位置情報サービス用ライブラリ

    // テストライブラリ
    testImplementation(libs.junit) // ユニットテスト用
    androidTestImplementation(libs.androidx.junit) // Androidテスト用JUnit
    androidTestImplementation(libs.androidx.espresso.core) // Espresso（UIテスト用）
}
