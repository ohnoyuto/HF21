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
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var infoBubble: View
    private val CAMERA_PERMISSION_CODE = 100

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
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // 東京マーカー
        val defaultLocation = LatLng(35.6895, 139.6917)
        val tokyoMarker = googleMap.addMarker(
            MarkerOptions()
                .position(defaultLocation)
                .title("東京")
        )

        // 新宿マーカー
        val shinjukuLocation = LatLng(35.6897, 139.7004)
        val shinjukuMarker = googleMap.addMarker(
            MarkerOptions()
                .position(shinjukuLocation)
                .title("新宿駅")
        )

        // 渋谷マーカー
        val shibuyaLocation = LatLng(35.6580, 139.7016)
        val shibuyaMarker = googleMap.addMarker(
            MarkerOptions()
                .position(shibuyaLocation)
                .title("渋谷駅")
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))

        googleMap.setOnMarkerClickListener { clickedMarker ->
            when (clickedMarker) {
                tokyoMarker -> {
                    showInfoBubble(
                        "東京",
                        "置いてあるバッテリーの種類: リチウムイオン",
                        "営業時間: 09:00~24:00",
                        5, // 利用可能数
                        0, // 返却可能数
                        "電話番号: 03-1234-5678",
                        "住所: 東京都新宿区"
                    )
                }
                shinjukuMarker -> {
                    showInfoBubble(
                        "新宿駅",
                        "置いてあるバッテリーの種類: ニッケル水素",
                        "営業時間: 05:00~24:00",
                        10, // 利用可能数
                        2, // 返却可能数
                        "電話番号: 03-2345-6789",
                        "住所: 東京都新宿区新宿3丁目"
                    )
                }
                shibuyaMarker -> {
                    showInfoBubble(
                        "渋谷駅",
                        "置いてあるバッテリーの種類: リチウムイオン",
                        "営業時間: 06:00~23:00",
                        8, // 利用可能数
                        1, // 返却可能数
                        "電話番号: 03-3456-7890",
                        "住所: 東京都渋谷区道玄坂"
                    )
                }
            }
            clickedMarker.showInfoWindow()
            true
        }
    }

    private fun showInfoBubble(locationName: String, batteryInfo: String, hours: String, availableUnits: Int, returnableUnits: Int, phone: String, address: String) {
        currentLocationName = locationName
        currentBatteryInfo = batteryInfo
        currentHours = hours
        currentAvailableUnits = availableUnits // 利用可能数
        currentReturnableUnits = returnableUnits // 返却可能数
        currentPhone = phone
        currentAddress = address

        findViewById<TextView>(R.id.battery_types).text = batteryInfo
        findViewById<TextView>(R.id.operating_hours).text = hours
        findViewById<TextView>(R.id.returnable_units).text = "返却可: ${returnableUnits}台"
        findViewById<TextView>(R.id.phone_number).text = phone
        findViewById<TextView>(R.id.address).text = address

        infoBubble.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}
