package com.pi12a082.hf21

// 必要なモジュールをインポート
import android.Manifest
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
    // カメラ権限コード
    private val CAMERA_PERMISSION_CODE = 100

    // メンバ変数
    private lateinit var mapView: MapView // 地図表示用のMapView
    private lateinit var googleMap: GoogleMap // Google Mapsインスタンス
    private lateinit var infoBubble: View // 情報バブル（詳細情報を表示）

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // レイアウトファイルの設定

        // MapView の初期化
        mapView = findViewById(R.id.mapView) // 地図ビューの参照
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this) // Google Mapの準備完了時にコールバックを設定

        // カメラ権限の確認とリクエスト
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }

        // 情報バブル（詳細情報の表示エリア）の初期化
        infoBubble = findViewById(R.id.info_bubble)
        infoBubble.visibility = View.GONE // 初期状態は非表示

        // 戻るボタンのクリックリスナー設定
        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish() // 現在のアクティビティを終了し、前の画面に戻る
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // 東京の初期マーカーを追加
        val defaultLocation = LatLng(35.6895, 139.6917) // 東京の緯度・経度
        val tokyoMarker = googleMap.addMarker(
            MarkerOptions()
                .position(defaultLocation)
                .title("東京") // マーカータイトル
        )

        // 新宿駅のマーカーを追加
        val shinjukuLocation = LatLng(35.6897, 139.7004) // 新宿駅の緯度・経度
        val shinjukuMarker = googleMap.addMarker(
            MarkerOptions()
                .position(shinjukuLocation)
                .title("新宿駅")
        )

        // 渋谷駅のマーカーを追加
        val shibuyaLocation = LatLng(35.6580, 139.7016) // 渋谷駅の緯度・経度
        val shibuyaMarker = googleMap.addMarker(
            MarkerOptions()
                .position(shibuyaLocation)
                .title("渋谷駅")
        )

        // カメラを東京に移動
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))

        // Google Maps UI設定
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true // ズームボタンを有効化
            isZoomGesturesEnabled = true // ピンチズームを有効化
            isScrollGesturesEnabled = true // スクロールを有効化
        }

        // マーカークリック時のリスナー設定
        googleMap.setOnMarkerClickListener { clickedMarker ->
            when (clickedMarker) {
                tokyoMarker -> {
                    showInfoBubble(
                        "東京",
                        "置いてあるバッテリーの種類: リチウムイオン",
                        "営業時間: 09:00~24:00",
                        "返却可能: 5台",
                        "電話番号: 03-1234-5678",
                        "住所: 東京都新宿区"
                    )
                }
                shinjukuMarker -> {
                    showInfoBubble(
                        "新宿駅",
                        "置いてあるバッテリーの種類: ニッケル水素",
                        "営業時間: 05:00~24:00",
                        "返却可能: 10台",
                        "電話番号: 03-2345-6789",
                        "住所: 東京都新宿区新宿3丁目"
                    )
                }
                shibuyaMarker -> {
                    showInfoBubble(
                        "渋谷駅",
                        "置いてあるバッテリーの種類: リチウムイオン",
                        "営業時間: 06:00~23:00",
                        "返却可能: 8台",
                        "電話番号: 03-3456-7890",
                        "住所: 東京都渋谷区道玄坂"
                    )
                }
            }
            clickedMarker.showInfoWindow() // デフォルトの吹き出しを表示
            true // クリック動作を消費
        }
    }

    private fun showInfoBubble(locationName: String, batteryInfo: String, hours: String, units: String, phone: String, address: String) {
        // 情報バブルの内容を更新
        findViewById<TextView>(R.id.battery_types).text = batteryInfo
        findViewById<TextView>(R.id.operating_hours).text = hours
        findViewById<TextView>(R.id.returnable_units).text = units
        findViewById<TextView>(R.id.phone_number).text = phone
        findViewById<TextView>(R.id.address).text = address

        // 情報バブルを表示
        infoBubble.visibility = View.VISIBLE
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // カメラ権限が付与された場合
            } else {
                // カメラ権限が拒否された場合
            }
        }
    }

    // MapView のライフサイクル管理
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
