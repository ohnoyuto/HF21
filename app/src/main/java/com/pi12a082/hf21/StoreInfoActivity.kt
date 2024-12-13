package com.pi12a082.hf21

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StoreInfoActivity : AppCompatActivity() {

    private var availableUnits = 0
    private var returnableUnits = 0

    // ユーザーの所持しているバッテリー数を管理
    private var userOwnedUnits = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_info)

        val storeName = findViewById<TextView>(R.id.store_name)
        val availableUnitsText = findViewById<TextView>(R.id.store_available_units)
        val returnableUnitsText = findViewById<TextView>(R.id.store_returnable_units)
        val storeHours = findViewById<TextView>(R.id.store_hours)
        val storeAddress = findViewById<TextView>(R.id.store_address)
        val storePhone = findViewById<TextView>(R.id.store_phone)
        val borrowButton = findViewById<Button>(R.id.qr_scan_button)
        val returnButton = findViewById<Button>(R.id.return_button)
        val backButton = findViewById<Button>(R.id.back_button)

        // Intentからデータを取得
        storeName.text = intent.getStringExtra("STORE_NAME")
        availableUnits = intent.getIntExtra("AVAILABLE_UNITS", 0)
        returnableUnits = intent.getIntExtra("RETURNABLE_UNITS", 0)

        availableUnitsText.text = "利用可: ${availableUnits}台"
        returnableUnitsText.text = "返却可: ${returnableUnits}台"
        storeHours.text = "営業時間: ${intent.getStringExtra("HOURS")}"
        storeAddress.text = "住所: ${intent.getStringExtra("ADDRESS")}"
        storePhone.text = "電話番号: ${intent.getStringExtra("PHONE")}"

        // ユーザーの所持バッテリー情報を取得
        userOwnedUnits = getUserOwnedUnits()

        // 借りるボタン
        borrowButton.setOnClickListener {
            if (userOwnedUnits > 0) {
                Toast.makeText(this, "すでにバッテリーを所持しています。返却してください。", Toast.LENGTH_SHORT).show()
            } else if (availableUnits > 0) {
                availableUnits--
                returnableUnits++
                userOwnedUnits++

                // 情報を更新
                availableUnitsText.text = "利用可: ${availableUnits}台"
                returnableUnitsText.text = "返却可: ${returnableUnits}台"

                saveUnitCounts(storeName.text.toString(), availableUnits, returnableUnits)
                saveUserOwnedUnits(userOwnedUnits)

                Toast.makeText(this, "バッテリーを借りました", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "借りられるバッテリーがありません", Toast.LENGTH_SHORT).show()
            }
        }

        // 返却ボタン
        returnButton.setOnClickListener {
            if (userOwnedUnits > 0 && returnableUnits > 0) {
                availableUnits++
                returnableUnits--
                userOwnedUnits--

                // 情報を更新
                availableUnitsText.text = "利用可: ${availableUnits}台"
                returnableUnitsText.text = "返却可: ${returnableUnits}台"

                saveUnitCounts(storeName.text.toString(), availableUnits, returnableUnits)
                saveUserOwnedUnits(userOwnedUnits)

                Toast.makeText(this, "バッテリーを返却しました", Toast.LENGTH_SHORT).show()
            } else if (userOwnedUnits == 0) {
                Toast.makeText(this, "返却するバッテリーがありません。", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "返却可能な台数がありません。", Toast.LENGTH_SHORT).show()
            }
        }

        // 戻るボタン
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun saveUnitCounts(location: String, availableUnits: Int, returnableUnits: Int) {
        val sharedPreferences = getSharedPreferences("BatteryInfo", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("${location}_available", availableUnits)
            putInt("${location}_returnable", returnableUnits)
            apply()
        }
    }

    private fun getUserOwnedUnits(): Int {
        val sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        return sharedPreferences.getInt("user_owned_units", 0)
    }

    private fun saveUserOwnedUnits(units: Int) {
        val sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("user_owned_units", units)
            apply()
        }
    }
}
