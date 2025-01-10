package com.pi12a082.hf21

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var infoBubble: View
    private val CAMERA_PERMISSION_CODE = 100

    // ピンを管理するマップ
    private val markerMap = mutableMapOf<String, Marker>()

    // 現在選択されている位置の情報を保持
    private var currentLocationName: String? = null
    private var currentBatteryInfo: String? = null
    private var currentHours: String? = null
    private var currentAvailableUnits: Int = 0 // 利用可能数
    private var currentReturnableUnits: Int = 0 // 返却可能数
    private var currentPhone: String? = null
    private var currentAddress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }

        // 情報バブルの初期化
        infoBubble = findViewById(R.id.info_bubble)
        infoBubble.visibility = View.GONE

        // 戻るボタンの設定
        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish()
        }

        // 情報バブルをクリックした際に `StoreInfoActivity` へ遷移
        infoBubble.setOnClickListener {
            val intent = Intent(this, StoreInfoActivity::class.java).apply {
                putExtra("STORE_NAME", currentLocationName)
                putExtra("BATTERY_INFO", currentBatteryInfo)
                putExtra("HOURS", currentHours)
                putExtra("AVAILABLE_UNITS", currentAvailableUnits) // 利用可能数を渡す
                putExtra("RETURNABLE_UNITS", currentReturnableUnits) // 返却可能数を渡す
                putExtra("PHONE", currentPhone)
                putExtra("ADDRESS", currentAddress)
            }
            startActivity(intent)
        }

        // 初期台数情報を保存
        saveInitialUnitCounts()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        setupMarkers()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(35.6895, 139.6917), 12f))

        googleMap.setOnMarkerClickListener { clickedMarker ->
            val locationName = markerMap.entries.find { it.value == clickedMarker }?.key
                ?: return@setOnMarkerClickListener false

            // 最新の台数情報を取得
            val (availableUnits, returnableUnits) = getUnitCounts(locationName)

            // 上部に情報を表示
            when (locationName) {
                "東京駅" -> showInfoBubble(
                    "東京駅",
                    "置いてあるバッテリーの種類: リチウムイオン",
                    "営業時間: 09:00~24:00",
                    availableUnits, // 利用可台数
                    returnableUnits, // 返却可台数
                    "電話番号: 03-1234-5678",
                    "住所: 東京都千代田区丸の内一丁目"
                )
                "新宿駅" -> showInfoBubble(
                    "新宿駅",
                    "置いてあるバッテリーの種類: ニッケル水素",
                    "営業時間: 05:00~24:00",
                    availableUnits,
                    returnableUnits,
                    "電話番号: 03-2345-6789",
                    "住所: 東京都新宿区新宿3丁目"
                )
                "渋谷駅" -> showInfoBubble(
                    "渋谷駅",
                    "置いてあるバッテリーの種類: リチウムイオン",
                    "営業時間: 06:00~23:00",
                    availableUnits,
                    returnableUnits,
                    "電話番号: 03-3456-7890",
                    "住所: 東京都渋谷区道玄坂"
                )
            }
            clickedMarker.showInfoWindow()
            true
        }
    }

    private fun setupMarkers() {
        val tokyoLocation = LatLng(35.681236, 139.767125)
        val tokyoMarker = googleMap.addMarker(MarkerOptions().position(tokyoLocation).title("東京駅"))
        markerMap["東京駅"] = tokyoMarker!!

        val shinjukuLocation = LatLng(35.6897, 139.7004)
        val shinjukuMarker = googleMap.addMarker(MarkerOptions().position(shinjukuLocation).title("新宿駅"))
        markerMap["新宿駅"] = shinjukuMarker!!

        val shibuyaLocation = LatLng(35.6580, 139.7016)
        val shibuyaMarker = googleMap.addMarker(MarkerOptions().position(shibuyaLocation).title("渋谷駅"))
        markerMap["渋谷駅"] = shibuyaMarker!!
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        refreshMarkers()
    }

    private fun refreshMarkers() {
        markerMap.forEach { (location, marker) ->
            val (availableUnits, returnableUnits) = getUnitCounts(location)
            marker.snippet = "利用可: ${availableUnits}台 / 返却可: ${returnableUnits}台"
            marker.showInfoWindow()
        }
    }

    private fun saveInitialUnitCounts() {
        val sharedPreferences = getSharedPreferences("BatteryInfo", MODE_PRIVATE)
        if (!sharedPreferences.contains("東京駅_available")) {
            with(sharedPreferences.edit()) {
                putInt("東京駅_available", 5) // 東京駅の利用可能台数
                putInt("東京駅_returnable", 3) // 東京駅の返却可能台数
                putInt("新宿駅_available", 10) // 新宿駅の利用可能台数
                putInt("新宿駅_returnable", 2) // 新宿駅の返却可能台数
                putInt("渋谷駅_available", 8) // 渋谷駅の利用可能台数
                putInt("渋谷駅_returnable", 1) // 渋谷駅の返却可能台数
                apply()
            }
        }
    }


    private fun getUnitCounts(location: String): Pair<Int, Int> {
        val sharedPreferences = getSharedPreferences("BatteryInfo", MODE_PRIVATE)
        val availableUnits = sharedPreferences.getInt("${location}_available", 0)
        val returnableUnits = sharedPreferences.getInt("${location}_returnable", 0)
        return Pair(availableUnits, returnableUnits)
    }


    private fun showInfoBubble(
        locationName: String,
        batteryInfo: String,
        hours: String,
        availableUnits: Int,
        returnableUnits: Int,
        phone: String,
        address: String
    ) {
        currentLocationName = locationName
        currentBatteryInfo = batteryInfo
        currentHours = hours
        currentAvailableUnits = availableUnits
        currentReturnableUnits = returnableUnits
        currentPhone = phone
        currentAddress = address

        findViewById<TextView>(R.id.battery_types).text = batteryInfo
        findViewById<TextView>(R.id.operating_hours).text = hours
        findViewById<TextView>(R.id.available_units).text = "利用可: ${availableUnits}台"
        findViewById<TextView>(R.id.returnable_units).text = "返却可: ${returnableUnits}台"
        findViewById<TextView>(R.id.phone_number).text = phone
        findViewById<TextView>(R.id.address).text = address

        infoBubble.visibility = View.VISIBLE
    }

}
