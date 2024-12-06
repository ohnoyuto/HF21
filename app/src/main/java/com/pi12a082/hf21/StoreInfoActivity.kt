package com.pi12a082.hf21

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StoreInfoActivity : AppCompatActivity() {

    // 利用可と返却可の台数を管理する変数
    private var availableUnits = 0
    private var returnableUnits = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_info)

        // UI要素の取得
        val storeName = findViewById<TextView>(R.id.store_name)
        val availableUnitsText = findViewById<TextView>(R.id.store_available_units)
        val returnableUnitsText = findViewById<TextView>(R.id.store_returnable_units)
        val storeHours = findViewById<TextView>(R.id.store_hours)
        val storeAddress = findViewById<TextView>(R.id.store_address)
        val storePhone = findViewById<TextView>(R.id.store_phone)
        val qrScanButton = findViewById<Button>(R.id.qr_scan_button)
        val backButton = findViewById<Button>(R.id.back_button)

        // Intentで渡されたデータを取得して設定
        storeName.text = intent.getStringExtra("STORE_NAME")
        availableUnits = intent.getIntExtra("AVAILABLE_UNITS", 0)
        returnableUnits = intent.getIntExtra("RETURNABLE_UNITS", 0)

        // 利用可と返却可のテキストを設定
        availableUnitsText.text = "利用可: ${availableUnits}台"
        returnableUnitsText.text = "返却可: ${returnableUnits}台"
        storeHours.text = "営業時間: ${intent.getStringExtra("HOURS")}"
        storeAddress.text = "住所: ${intent.getStringExtra("ADDRESS")}"
        storePhone.text = "電話番号: ${intent.getStringExtra("PHONE")}"

        // QRスキャンボタンの処理
        qrScanButton.setOnClickListener {
            if (availableUnits > 0) {
                // 利用可の台数を減らし、返却可の台数を増やす
                availableUnits--
                returnableUnits++

                // テキストを更新
                availableUnitsText.text = "利用可: ${availableUnits}台"
                returnableUnitsText.text = "返却可: ${returnableUnits}台"

                // トーストで通知
                Toast.makeText(this, "バッテリーを借りました", Toast.LENGTH_SHORT).show()
            } else {
                // 利用可能な台数が0の場合
                Toast.makeText(this, "借りられるバッテリーがありません", Toast.LENGTH_SHORT).show()
            }
        }

        // 戻るボタンの処理
        backButton.setOnClickListener {
            finish() // この画面を終了
        }
    }
}
