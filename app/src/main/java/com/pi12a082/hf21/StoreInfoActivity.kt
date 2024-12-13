package com.pi12a082.hf21

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

class StoreInfoActivity : AppCompatActivity() {

    private var availableUnits = 0
    private var returnableUnits = 0
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

        storeName.text = intent.getStringExtra("STORE_NAME")
        availableUnits = intent.getIntExtra("AVAILABLE_UNITS", 0)
        returnableUnits = intent.getIntExtra("RETURNABLE_UNITS", 0)

        availableUnitsText.text = "利用可: ${availableUnits}台"
        returnableUnitsText.text = "返却可: ${returnableUnits}台"
        storeHours.text = "営業時間: ${intent.getStringExtra("HOURS")}"
        storeAddress.text = "住所: ${intent.getStringExtra("ADDRESS")}"
        storePhone.text = "電話番号: ${intent.getStringExtra("PHONE")}"

        userOwnedUnits = getUserOwnedUnits()

        borrowButton.setOnClickListener {
            handleBorrowAction(storeName, availableUnitsText, returnableUnitsText)
        }

        returnButton.setOnClickListener {
            handleReturnAction(storeName, availableUnitsText, returnableUnitsText)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun handleBorrowAction(
        storeName: TextView,
        availableUnitsText: TextView,
        returnableUnitsText: TextView
    ) {
        if (userOwnedUnits > 0) {
            Toast.makeText(this, "すでにバッテリーを所持しています。返却してください。", Toast.LENGTH_SHORT).show()
        } else if (availableUnits > 0) {
            availableUnits--
            returnableUnits++
            userOwnedUnits++

            availableUnitsText.text = "利用可: ${availableUnits}台"
            returnableUnitsText.text = "返却可: ${returnableUnits}台"

            saveUnitCounts(storeName.text.toString(), availableUnits, returnableUnits)
            saveUserOwnedUnits(userOwnedUnits)
            addHistory("借りる", storeName.text.toString())

            Toast.makeText(this, "バッテリーを借りました", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "借りられるバッテリーがありません", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleReturnAction(
        storeName: TextView,
        availableUnitsText: TextView,
        returnableUnitsText: TextView
    ) {
        if (userOwnedUnits > 0 && returnableUnits > 0) {
            availableUnits++
            returnableUnits--
            userOwnedUnits--

            availableUnitsText.text = "利用可: ${availableUnits}台"
            returnableUnitsText.text = "返却可: ${returnableUnits}台"

            saveUnitCounts(storeName.text.toString(), availableUnits, returnableUnits)
            saveUserOwnedUnits(userOwnedUnits)
            addHistory("返却", storeName.text.toString())

            Toast.makeText(this, "バッテリーを返却しました", Toast.LENGTH_SHORT).show()
        } else if (userOwnedUnits == 0) {
            Toast.makeText(this, "返却するバッテリーがありません。", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "返却可能な台数がありません。", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addHistory(action: String, storeName: String) {
        val sharedPreferences = getSharedPreferences("HistoryInfo", MODE_PRIVATE)
        val historySet = sharedPreferences.getStringSet("history_list", mutableSetOf()) ?: mutableSetOf()
        val timestamp = System.currentTimeMillis()
        historySet.add("$action: $storeName - $timestamp")
        with(sharedPreferences.edit()) {
            putStringSet("history_list", historySet)
            apply()
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
